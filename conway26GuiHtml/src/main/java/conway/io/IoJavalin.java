package conway.io;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsMessageContext;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import main.java.conway.domain.*;
import java.util.regex.*;

public class IoJavalin implements IOutDev{
	
	private WsMessageContext pageCtx ;
	private GameController lifeController;
	
	public IoJavalin() {
		
		lifeController = new LifeController(new Life(20,20),(IOutDev)this);
		
        var app = Javalin.create(config -> {
			config.staticFiles.add(staticFiles -> {
				staticFiles.directory = "/page";
				staticFiles.location = Location.CLASSPATH; // Cerca dentro il JAR/Classpath
				/*
				 * i file sono "impacchettati" con il codice, non cercati sul disco rigido esterno.
				 */
		    });
		}).start(8080);//ci mettiamo su porta 8080	
 
/*
 * --------------------------------------------
 * Parte HTTP        
 * --------------------------------------------
 */
        app.get("/", ctx -> {
    		//Path path = Path.of("./src/main/resources/page/ConwayInOutPage.html");    		    
        	/*
        	 * Java cercherà il file all'interno del Classpath 
        	 * (dentro il JAR o nelle cartelle dei sorgenti di Eclipse), 
        	 * rendendo il codice universale
         	 */
        	var inputStream = getClass().getResourceAsStream("/page/ConwayInOutPage.html");       	
        	if (inputStream != null) {
        		// Trasformiamo l'inputStream in stringa (o lo mandiamo come stream)
        	    String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        	    ctx.html(content);
        	} else {
		        ctx.status(404).result("File non trovato nel file system");
		    }
		    //ctx.result("Hello from Java!"));  //la forma più semplice di risposta
        }); 
        
        app.get("/greet/{name}", ctx -> {
            String name = ctx.pathParam("name");
            ctx.result("Hello, " + name + "!");
        }); //http://localhost:8080/greet/Alice
        
        app.get("/api/users", ctx -> {
            Map<String, Object> user = Map.of("id", 1, "name", "Bob");
            ctx.json(user); // Auto-converts to JSON
        });
        
        /*
         * Javalin v5+: Si passa solo la "promessa" (il Supplier del Future). 
         * Javalin è diventato più intelligente: se il Future restituisce una Stringa, 
         * lui fa ctx.result(stringa). Se restituisce un oggetto, lui fa ctx.json(oggetto).
         * 
         */
        app.get("/async", ctx -> {
        	ctx.future(() -> {
	        	// Creiamo il future
	            CompletableFuture<String> future = new CompletableFuture<>();
	            
	            // Eseguiamo il lavoro in un altro thread
	            new Thread(() -> { 
	                try {
	                    Thread.sleep(2000); // Simulazione calcolo pesante
	                    future.complete("IoJavalin | Risultato calcolato asincronamente");
	                } catch (Exception e) {
	                    future.completeExceptionally(e);
	                }
	            });
	            
	            return future; // Restituiamo il future a Javalin
        	});
        });
        
        app.get("/async1", ctx -> {
            ctx.future(() -> CompletableFuture.supplyAsync(() -> {
                // Simuliamo l'operazione lenta
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "IoJavalin | Risultato calcolato con supplyAsync";
            }));
        });
/*
 * --------------------------------------------
 * Parte Websocket
 * --------------------------------------------
 */
        
        app.ws("/chat", ws -> {
            ws.onConnect(ctx -> CommUtils.outgreen("Client connected chat!"));
            ws.onMessage(ctx -> {
                String message = ctx.message();
                CommUtils.outcyan("IoJavalin |  riceve:" + message);
                ctx.send("Echo: " + message);
            });
        });
        
        app.ws("/eval", ws -> {
            ws.onConnect(ctx -> CommUtils.outgreen("IoJavalin | Client connected eval"));
            
            ws.onMessage(ctx -> {
                String message = ctx.message();     
                CommUtils.outblue("IoJavalin |  eval receives:" + message );
                try {
                	IApplMessage m = new ApplMessage(message);
                    CommUtils.outblue("IoJavalin |  eval:" + m.msgContent() );
                    if( m.msgContent().equals("ready")) { 
                    	pageCtx = ctx;  //memorizzo connession pagina
                    	
                    	
                    	
                    }else if( m.msgContent().contains("cell(")) { 
                    	//faccio decodifica del messaggio estrapolando le coordinate della cella
                    	Pattern pattern = Pattern.compile("cell\\((\\d+),(\\d+)\\)");
                        Matcher matcher = pattern.matcher(m.msgContent());
                        matcher.find();
                        int x = Integer.parseInt(matcher.group(1));
                        int y = Integer.parseInt(matcher.group(2));
                        
                        //invoco il metodo del controller per cambiare lo stato di una cella
                    	lifeController.switchCellState(x, y);
                    	
                    	
                    }else if (m.msgContent().contains("start")){//comando per cominciare la simulazione
                    	lifeController.onStart();
                    	
                    }else if (m.msgContent().contains("stop")){//comando per fermare la simulazione
                    	lifeController.onStop();
                    
                    }else if (m.msgContent().contains("clear")){//comando per pulire la griglia
                    	lifeController.onClear();
                    
                    }else ctx.send(m.msgContent());
                }catch(Exception e) {
                	CommUtils.outred("IoJavalin |  error:" + e.getMessage());
                }               
            });
        });        
	}
	
	@Override //IOutDev
	public void display(String msg) {
		
	}

 	
	@Override //IOutDev
	public void displayCell(IGrid grid, int x, int y) {
		//qua praticamente dobbiamo ri costruire il messaggio per il client?
		int isAlive = grid.getCellValue(x, y)?1:0;
		CommUtils.outgreen("eval sends to client: cell("+x+","+y+","+isAlive+")");
		pageCtx.send("cell("+x+","+y+","+isAlive+")");
		
	}
	
	@Override //IOutDev
	public void displayGrid(IGrid newGrid) {
		for(int i = 0; i < newGrid.getRowsNum(); i++) {
			for(int j = 0; j < newGrid.getColsNum(); j++) {
				this.displayCell(newGrid,i,j);
			}
		}
	}
	
	@Override //IOutDev
	public void close() {
	}

	
	public static void main(String[] args) {//questo refuso mi sa
		var resource = IoJavalin.class.getResource("/pages");
		CommUtils.outgreen("DEBUG: La cartella /page si trova in: " + resource);
		new IoJavalin();
	}

}
