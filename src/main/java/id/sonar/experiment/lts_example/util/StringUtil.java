package id.sonar.experiment.lts_example.util;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.ObjectUtils;

public class StringUtil extends StringUtils{

	public static String NEWLINE = Character.toString((char) 10);
	private static String regex = "https://(www.)?facebook.com/.+/(photos|media|videos)/.+";
	public static String splitCamelCase(String s) {
		   return firstCapitalize(s.replaceAll(
		      String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      ),
		      " "
		   ));
		}
	
	public static String firstCapitalize(String str) {
	    if (str == null || str.isEmpty())
			return str;

	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String getDomainName(String url) {
	    URI uri = URI.create(url);
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	public static boolean isNullOrEmpty(String s) {
		if (Objects.isNull(s))
			return true;

		return s.trim().isEmpty();
	}
	
	public static String getTypeFacebook(String url) {
		if (Objects.isNull(url))
			return "post";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);
		String type = "";
		if (m.find())
			type = m.group(2);
		
		if (ObjectUtils.isEmpty(type)) {
			if (url.contains("photo.php"))
				type = "photo";
			else if (!url.contains("facebook.com"))
				type = "media";
			else 
				type = "post";
		} else {
			if (type.equals("videos"))
				type = "video";
			else if (type.equals("photos"))
				type = "photo";
		}

		return type;
	}
	
	
	public static String getIdTiktok(String link) {
		String [] split = link.split("/");
		return split[split.length-1];
	}
	
	public static boolean isContain(String source, String subItem){
        String pattern = ""+subItem+"+\\b";
        Pattern p=Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher m=p.matcher(source);
        return m.find();
   }
	
	public static Pair<Integer, Integer> isContainIndex(String source, String subItem){
        String pattern = ""+subItem+"+\\b";
        Pattern p=Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher m=p.matcher(source);
        if(m.find())
        	return Pair.of(m.start(), m.end());
        else
        	return Pair.of(0, 0);
   }


	/**
	 * 
	 * Cut string if exceed max length and add ellipsis(...)
	 * example: </br>
	 * <code> 
	 * maximum 5 data </br>
	 * "12345" 	return "12345" </br>
	 * "123456"	return "12..." </br>
	 * "123"	return "123" </br>
	 * ""		return "" </br>
	 * null 	return null </br>
	 * </code>
	 * @param val
	 * @param maxLength 
	 * @return
	 */
	public static String omitString(String val, int maxLength) {
		if(StringUtil.isEmpty(val)) {
			return val;
		}
		if(val.length()<=maxLength) {
			return val;
		}else {
			String cutString = val.substring(0, maxLength-3);
			return cutString.concat("...");
		}
		
	}
	
	/**
	 * 
	 * ^remove all character that are not: 
	 * letters \p{L}, 
	 * numeric \p{Nd}, 
	 * mark \p{M}, 
	 * punctuation \p{P}, 
	 * whitespace \s
	 * </br>
	 * <code>[^\p{L}\p{Nd}\p{M}\p{P}\s]</code>
	 * 
	 * @param val
	 * @return
	 */
	public static String removeUnreadable(String val) {
		if(StringUtil.isEmpty(val)) {
			return val;
		}
		String unreadableRegex= "[^\\p{L}\\p{Nd}\\p{M}\\p{P}\\s]"; 
		String firstClean = val.replaceAll(unreadableRegex, "");
		//clean again, because firstClean causes some unknown char becomes ?
		return firstClean.replaceAll(unreadableRegex, "");
	}
	
	public static String translateSentiment(Integer val) {
		String sentiment = EMPTY;
		switch (val) {
		case -1:
			sentiment = "Negative";
			break;
		case 0:
			sentiment = "Neutral";
			break;
		case 1:
			sentiment = "Positive";
			break;
		default:
			sentiment = "Neutral";
			break;
		}
		
		return sentiment;
	}
	
}
