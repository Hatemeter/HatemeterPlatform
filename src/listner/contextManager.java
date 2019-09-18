package listner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import eu.fbk.dh.kd.lib.KD_configuration;
import eu.fbk.dh.kd.lib.KD_core;
import eu.fbk.dh.kd.lib.KD_loader;
import eu.fbk.dh.kd.lib.KD_configuration.ColumExtraction;
import eu.fbk.dh.kd.lib.KD_configuration.Group;
import eu.fbk.dh.kd.lib.KD_configuration.Prefer_Specific_Concept;
import eu.fbk.dh.kd.lib.KD_configuration.Tagset;
import eu.fbk.dh.kd.lib.KD_core.Language;
import eu.fbk.dh.tint.runner.TintPipeline;

/**
 * Application Lifecycle Listener implementation class contextManager
 *
 */
@WebListener
public class contextManager implements ServletContextListener {
	private final static Logger LOGGER = Logger.getLogger(contextManager.class.getName());

	/**
	 * Default constructor.
	 */
	public contextManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.log(Level.INFO, "Update KD definition");
		String languagePackPath = arg0.getServletContext().getRealPath("/WEB-INF/lib/languages");
		KD_configuration configuration = new KD_configuration();
		configuration.numberOfConcepts = 20;
		configuration.max_keyword_length = 6;
		configuration.local_frequency_threshold = 2;
		configuration.prefer_specific_concept = Prefer_Specific_Concept.MEDIUM;
		configuration.skip_proper_noun = false;
		configuration.skip_keyword_with_proper_noun = false;
		configuration.skip_keyword_with_not_allowed_words = true;
		configuration.rerank_by_position = false;
		configuration.group_by = Group.NONE;
		configuration.only_multiword = false;
		configuration.column_configuration = ColumExtraction.CUSTOM;
		configuration.languagePackPath = languagePackPath;
		configuration.token_position = 0;
		configuration.lemma_position = 2;
		configuration.pos_position = 1;
		configuration.tagset = Tagset.TEXTPRO;

		KD_loader.run_the_updater(Language.ITALIAN, configuration.languagePackPath);
		KD_loader.run_the_updater(Language.ENGLISH, configuration.languagePackPath);
		KD_core.Language french = Language.CUSTOM;
		french.set_Custom_Language("FRENCH");
		KD_loader.run_the_updater(french, configuration.languagePackPath);

		// KD_loader.run_the_updater(french, configuration.languagePackPath);
		// KD_loader.run_the_updater(Language.CUSTOM, configuration.languagePackPath);
		LOGGER.log(Level.INFO, "End Update KD definition");

		Properties props = new Properties();
		props.setProperty("ssplit.newlineIsSentenceBreak", "always");
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,keyphrase");
		props.setProperty("ner.useSUTime", "false");
		props.setProperty("ner.applyFineGrained", "false");
		props.setProperty("customAnnotatorClass.keyphrase", "eu.fbk.dh.kd.annotator.DigiKDAnnotator");
		props.setProperty("keyphrase.numberOfConcepts", "20");
		props.setProperty("keyphrase.local_frequency_threshold", "2");
		props.setProperty("keyphrase.languageFolder", languagePackPath);

		StanfordCoreNLP en_pipeline = new StanfordCoreNLP(props);

		arg0.getServletContext().setAttribute("en_pipeline", en_pipeline);
		
		TintPipeline it_pipeline = new TintPipeline();
		try {
		it_pipeline.loadDefaultProperties();
		}catch (Exception e) {
			e.printStackTrace();
		}
		it_pipeline.setProperty("annotators", "ita_toksent, pos,ita_morpho, ita_lemma,keyphrase");
		it_pipeline.setProperty("nthread", "6");
		it_pipeline.setProperty("customAnnotatorClass.keyphrase", "eu.fbk.dh.kd.annotator.DigiKDAnnotator");
		it_pipeline.setProperty("keyphrase.numberOfConcepts", "20");
		it_pipeline.setProperty("keyphrase.local_frequency_threshold", "2");
		it_pipeline.setProperty("keyphrase.language", "ITALIAN");
		it_pipeline.setProperty("keyphrase.languageFolder", languagePackPath);

        // ((TintPipeline) pipeline).setProperty("ita_toksent.model", "/Users/giovannimoretti/Dropbox/DropBoxesCodes/SPMT/token-settings.xml");
		it_pipeline.load();
		arg0.getServletContext().setAttribute("it_pipeline", it_pipeline);

		
		
		
		 Properties propsFr = new Properties();

		 propsFr.setProperty("ssplit.newlineIsSentenceBreak", "always");
		 propsFr.setProperty("annotators", "tokenize,ssplit,pos,lemma,keyphrase");
		 propsFr.setProperty("ner.useSUTime", "false");
		 propsFr.setProperty("ner.applyFineGrained", "false");
		 propsFr.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/french/french.tagger");
		 propsFr.setProperty("tokenize.language", "fr");
		 propsFr.setProperty("'pipelineLanguage'", "fr");
		 propsFr.setProperty("customAnnotatorClass.keyphrase", "eu.fbk.dh.kd.annotator.DigiKDAnnotator");
		 propsFr.setProperty("keyphrase.numberOfConcepts", "20");
		 propsFr.setProperty("keyphrase.local_frequency_threshold", "2");
		 propsFr.setProperty("keyphrase.language", "CUSTOM");
		 propsFr.setProperty("keyphrase.languageName", "FRENCH");
		 propsFr.setProperty("keyphrase.languageFolder", languagePackPath);

		 StanfordCoreNLP fr_pipeline = new StanfordCoreNLP(propsFr);

			
		 arg0.getServletContext().setAttribute("fr_pipeline", fr_pipeline);
		
		LOGGER.log(Level.INFO, "Read Twitter Keys");
		
		ConcurrentLinkedQueue<JsonObject> keys = new ConcurrentLinkedQueue<JsonObject>();
		Gson gson = new Gson();
		try {
			JsonReader reader = new JsonReader(new FileReader("/data/giovanni/hatemeter/keys.json"));
			JsonArray keysArray = gson.fromJson(reader, JsonArray.class);
			keysArray.forEach(k -> {
				keys.add((JsonObject)k);
			});	
			arg0.getServletContext().setAttribute("twKeys", keys);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
