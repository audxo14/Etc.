package Tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Tfidf {
	static List<HashMap<String, Double>> word_freq = new ArrayList<HashMap<String, Double>>();
	private static String[] eng_stop = {"a", "as", "able", "about", "above", "according", 
			"accordingly", "across", "actually", "after", "afterwards", "again", "against", 
			"aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", 
			"although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", 
			"anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", 
			"appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available",
			"away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", 
			"beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", 
			"beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", 
			"cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", 
			"concerning", "consequently", "consider", "considering", "contain", "containing", "contains", 
			"corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", 
			"did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", 
			"during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", 
			"especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", 
			"ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", 
			"follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", 
			"getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", 
			"happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", 
			"her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", 
			"his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", 
			"immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", 
			"instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", 
			"kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", 
			"let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", 
			"maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", 
			"my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", 
			"nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", 
			"nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", 
			"once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves",
			"out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", 
			"plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", 
			"reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw",
			"say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", 
			"self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", 
			"shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes",
			"somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", 
			"sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", 
			"thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", 
			"thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", 
			"theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", 
			"throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", 
			"try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", 
			"upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", 
			"vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", 
			"were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", 
			"whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", 
			"whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", 
			"wonder", "would", "would", "wouldnt", "www", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", 
			"yours", "yourself", "yourselves", "zero", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "b", "c", 
			"d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	
	public static double tf(List<String> doc, String term)
	{
		double result = 0;
		for (String word : doc)
		{
			if (term.equalsIgnoreCase(word))
				result++;
		}
		
		return result / doc.size();
	}
	
	public static double idf(List<String> docs, String term)
	{
		List<String> doc_words = new ArrayList<String>();
		double n = 0;
		for (String doc: docs)
		{	
			doc_words = doc_to_list(doc);
			for (String word: doc_words)
			{
				if (term.equalsIgnoreCase(word))
				{
					n++;
					break;
				}
			}
		}
		
		return Math.log(1 + docs.size() / n);
	}
	
	private static List<String> doc_to_list (String docu)
	{
		List<String> result = new ArrayList<String>();
		Stream<String> stream;
		String[] stringArray;
		
		stream = Stream.of(docu.split("\\W+")).parallel();
		stringArray = stream.toArray(size -> new String[size]);
		for (String word:stringArray)
		{
			result.add(word);
		}
		
		return result;
	}
	
	private static HashMap<String, Double> TfandIdf(List<String> doc_words, List<String> docs)
	{
		HashMap<String, Double> result = new HashMap<String, Double>();
		for (String word: doc_words)
		{
			double tmp_val = tf(doc_words, word) * idf(docs, word) ;
			result.put(word, tmp_val);
		}
		
		return result;
	}
	
	public List<HashMap<String, Double>> main(List<String> docs, String[] stop_list)
	{
		List<String> doc_words = new ArrayList<String>();
		HashMap<String, Double> tmp_map = new HashMap<String, Double>();
		
		for (String docu: docs)
		{
			doc_words = doc_to_list(docu);
			
			for(Iterator<String> it = doc_words.iterator() ; it.hasNext() ; )
			{
				String value = it.next();	
				
				for (String stop_word: eng_stop)
				{
					if (stop_word.equals(value))
					{						
						it.remove();
						break;
					}
				}
				
				for (String stop_word: stop_list)
				{
					if (stop_word.trim().equals(value))
					{						
						it.remove();
						break;
					}					
				}
			}
			tmp_map = TfandIdf(doc_words, docs); 
			
			word_freq.add(tmp_map);
		}
		
		return word_freq;
	}

}
