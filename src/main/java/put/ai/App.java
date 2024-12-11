package put.ai;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class App {
	static LayoutUI ui;
	
	private static void initializeUI() {
    	ui = new LayoutUI();
    }

    public static final void main(String[] args) {
    	initializeUI();
//        try {
//            // load up the knowledge base
//	        KieServices ks = KieServices.Factory.get();
//    	    KieContainer kContainer = ks.getKieClasspathContainer();
//        	KieSession kSession = kContainer.newKieSession("ksession-rules");
//
//            kSession.fireAllRules();
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
    }
}
