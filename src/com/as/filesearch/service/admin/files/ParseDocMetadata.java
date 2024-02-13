
package com.as.filesearch.service.admin.files;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.DocumentSelector;
import org.apache.tika.io.IOUtils;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;

import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.helper.DateHelper;
import com.as.filesearch.entity.SysFileMeta;
import com.as.filesearch.service.admin.files.serialization.JsonMetadataList;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.RecursiveParserWrapper;
import org.apache.tika.parser.html.BoilerpipeContentHandler;
import org.apache.tika.sax.BasicContentHandlerFactory;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Simple Swing GUI for Apache Tika. You can drag and drop files on top
 * of the window to have them parsed.
 */
public class ParseDocMetadata {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5883906936187059496L;

    /**
     * Main method. Sets the Swing look and feel to the operating system
     * settings, and starts the Tika GUI with an {@link AutoDetectParser}
     * instance as the default parser.
     *
     * @param args ignored
     * @throws Exception if an error occurs
     */
    public static void main(String[] args) throws Exception {
        TikaConfig config = TikaConfig.getDefaultConfig();
        if (args.length > 0) {
            File configFile = new File(args[0]);
            config = new TikaConfig(configFile);
        }
    }

    /*
     * Metadata of doc file
     */
    private SysFileMeta metaData = null;

    /*
     * Session ojbect
     */
    HttpServletRequest request;
    /*
     * File path
     */
    private String filePath = null;

    //maximum length to allow for mark for reparse to get JSON
    private final int MAX_MARK = 20*1024*1024;//20MB
    /**
     * Parsing context.
     */
    private final ParseContext context;

    /**
     * Configured parser instance.
     */
    private final Parser parser;
    
    /**
     * Captures requested embedded images
     */
    private final ImageSavingParser imageParser;


    public ParseDocMetadata(String configFile, SysFileMeta metaData, HttpServletRequest request) {

        TikaConfig config = TikaConfig.getDefaultConfig();

        this.parser = new AutoDetectParser(config);
        this.context = new ParseContext();

        this.imageParser = new ImageSavingParser(parser);
        this.context.set(DocumentSelector.class, new ImageDocumentSelector());
        this.context.set(Parser.class, imageParser);
        
        this.filePath = configFile;
        this.metaData = metaData;
        this.request = request;
    }

    public void openFile() {
    	File file = new File(this.filePath);
        try {
            Metadata tikaMeta = new Metadata();
            TikaInputStream stream = TikaInputStream.get(file, tikaMeta);
            try {
                handleStream(stream, tikaMeta);
            } finally {
                stream.close();
            }
        } catch (Throwable t) {
            handleError(file.getPath(), t);
        }
    }


    private void handleStream(InputStream input, Metadata tikaMeta)
            throws Exception {
        StringWriter htmlBuffer = new StringWriter();
        StringWriter textBuffer = new StringWriter();
        StringWriter textMainBuffer = new StringWriter();
        StringWriter xmlBuffer = new StringWriter();
        StringBuilder metadataBuffer = new StringBuilder();

        ContentHandler handler = new TeeContentHandler(
                getHtmlHandler(htmlBuffer),
                getTextContentHandler(textBuffer),
                getTextMainContentHandler(textMainBuffer),
                getXmlContentHandler(xmlBuffer));

        context.set(DocumentSelector.class, new ImageDocumentSelector());
        if (input.markSupported()) {
            input.mark(MAX_MARK);
        }
//        input = new ProgressMonitorInputStream(
//                this, "Parsing stream", input);
        parser.parse(input, handler, tikaMeta, context);

        String[] names = tikaMeta.names();
        Arrays.sort(names);
        for (String name : names) {
            metadataBuffer.append(name);
            metadataBuffer.append(": ");
            metadataBuffer.append(tikaMeta.get(name));
            metadataBuffer.append("\n");
        }
        System.out.println(metadataBuffer);
        String name = tikaMeta.get(Metadata.RESOURCE_NAME_KEY);
        if (name != null && name.length() > 0) {
            System.out.println("Apache Tika: " + name);
        } else {
        	System.out.println("Apache Tika: unnamed document");
        }

        /*
         * Set meta data
         */
        metaData.setCaseNo(TextRegular.testCaseNo(textBuffer.toString()));
        metaData.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        metaData.setClerk(TextRegular.testClerk(textBuffer.toString()));
        metaData.setContentPlain(textBuffer.toString());
        metaData.setContentHtml(htmlBuffer.toString());

		this.request.getSession().setAttribute(Constant._SESSION_CONTENT_PLAIN, textBuffer.toString());
		this.request.getSession().setAttribute(Constant._SESSION_CONTENT_HTML, htmlBuffer.toString());

        metaData.setCourt(TextRegular.testCourt(textBuffer.toString()));
        metaData.setCreateDate(DateHelper.today("yyyy-MM-dd"));
        metaData.setModifyDate(DateHelper.today("yyyy-MM-dd"));
        metaData.setJudgeDate(TextRegular.testJudgeDate(textBuffer.toString()));
        if (metaData.getJudgeDate() != null && !"".equals(metaData.getJudgeDate())) {
        	int judgeYear = Integer.parseInt(metaData.getJudgeDate().substring(0, 4));
        	metaData.setJudgeYear(judgeYear);
        }
        metaData.setExcuted(TextRegular.testExcuted(textBuffer.toString()));
        metaData.setExcutor(TextRegular.testExcutor(textBuffer.toString()));
        metaData.setChiefJudge(TextRegular.testChiefJudge(textBuffer.toString()));
        metaData.setJudge(TextRegular.testJudge(textBuffer.toString()));
        metaData.setGuideCase(false);
        if (metaData.getCaseNo() != null && !"".equals( metaData.getCaseNo())) {
			if (metaData.getCaseNo().contains("再终字")) {
				metaData.setStep("再审二审");
			} else if (metaData.getCaseNo().contains("再初字")) {
				metaData.setStep("再审一审");
			} else if (metaData.getCaseNo().contains("终字")) {
				metaData.setStep("二审");
			} else if (metaData.getCaseNo().contains("初字")) {
				metaData.setStep("一审");
			} else if (metaData.getCaseNo().contains("复字")) {
				metaData.setStep("执行复议");
			} else if (metaData.getCaseNo().contains("执字")) {
				metaData.setStep("执行程序");
			} else if (metaData.getCaseNo().contains("赔字")) {
				metaData.setStep("国家赔偿");
			} else if (metaData.getCaseNo().contains("申字")) {
				metaData.setStep("再审一审");
			}
        }
//        String title = tikaMeta.get("title");
        String fileName = tikaMeta.get("resourceName");
    	String title = fileName.substring(0, fileName.lastIndexOf('.'));
        metaData.setTitle(title);
        // Cause can be parsed from the title
        metaData.setCause(TextRegular.testCause(title));
        metaData.setType(TextRegular.testType(textBuffer.toString()));
        // lawyers and lawfirms
		List<String> arr = TextRegular.testLawyers(textBuffer.toString());
		if (arr.size() == 2) {
			metaData.setLawyers(arr.get(0));
			metaData.setLawFirm(arr.get(1));
		}
		// Legal person
        metaData.setLegalPerson(TextRegular.testLegalPerson(textBuffer.toString()));
		// Third part
        metaData.setThirdPart(TextRegular.testThirdPart(textBuffer.toString()));
        // file meta
        metaData.setFileName(tikaMeta.get("resourceName"));
        metaData.setFileSize(Integer.parseInt(tikaMeta.get("Content-Length")));
        metaData.setFileType(tikaMeta.get("Content-Type"));
        metaData.setUploadDate(DateHelper.today("yyyy-MM-dd HH:mm:ss"));

        if (!input.markSupported()) {
        	System.out.println("InputStream does not support mark/reset for Recursive Parsing");
            return;
        }
        boolean isReset = false;
        try {
            input.reset();
            isReset = true;
        } catch (IOException e) {
        	System.out.println("Error during stream reset.\n"+
                    "There's a limit of "+MAX_MARK + " bytes for this type of processing in the GUI.\n"+
                    "Try the app with command line argument of -J."
            );
        }
        if (isReset) {
            RecursiveParserWrapper wrapper = new RecursiveParserWrapper(parser,
                    new BasicContentHandlerFactory(
                            BasicContentHandlerFactory.HANDLER_TYPE.BODY, -1));
            wrapper.parse(input, null, new Metadata(), new ParseContext());
            StringWriter jsonBuffer = new StringWriter();
            JsonMetadataList.setPrettyPrinting(true);
            JsonMetadataList.toJson(wrapper.getMetadata(), jsonBuffer);
            System.out.println(jsonBuffer.toString());
        }
    }

    private void handleError(String name, Throwable t) {
        StringWriter writer = new StringWriter();
        writer.append("Apache Tika was unable to parse the document\n");
        writer.append("at " + name + ".\n\n");
        writer.append("The full exception stack trace is included below:\n\n");
        t.printStackTrace(new PrintWriter(writer));
    }

    /**
     * Creates and returns a content handler that turns XHTML input to
     * simplified HTML output that can be correctly parsed and displayed
     * by {@link JEditorPane}.
     * <p>
     * The returned content handler is set to output <code>html</code>
     * to the given writer. The XHTML namespace is removed from the output
     * to prevent the serializer from using the &lt;tag/&gt; empty element
     * syntax that causes extra "&gt;" characters to be displayed.
     * The &lt;head&gt; tags are dropped to prevent the serializer from
     * generating a &lt;META&gt; content type tag that makes
     * {@link JEditorPane} fail thinking that the document character set
     * is inconsistent.
     * <p>
     * Additionally, it will use ImageSavingParser to re-write embedded:(image) 
     * image links to be file:///(temporary file) so that they can be loaded.
     *
     * @param writer output writer
     * @return HTML content handler
     * @throws TransformerConfigurationException if an error occurs
     */
    private ContentHandler getHtmlHandler(Writer writer)
            throws TransformerConfigurationException {
        SAXTransformerFactory factory = (SAXTransformerFactory)
            SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
        handler.setResult(new StreamResult(writer));
        return new ContentHandlerDecorator(handler) {
            @Override
            public void startElement(
                    String uri, String localName, String name, Attributes atts)
                    throws SAXException {
                if (XHTMLContentHandler.XHTML.equals(uri)) {
                    uri = null;
                }
                if (!"head".equals(localName)) {
                    if("img".equals(localName)) {
                       AttributesImpl newAttrs;
                       if(atts instanceof AttributesImpl) {
                          newAttrs = (AttributesImpl)atts;
                       } else {
                          newAttrs = new AttributesImpl(atts);
                       }
                       
                       for(int i=0; i<newAttrs.getLength(); i++) {
                          if("src".equals(newAttrs.getLocalName(i))) {
                             String src = newAttrs.getValue(i);
                             if(src.startsWith("embedded:")) {
                                String filename = src.substring(src.indexOf(':')+1);
                                try {
                                   File img = imageParser.requestSave(filename);
                                   String newSrc = img.toURI().toString();
                                   newAttrs.setValue(i, newSrc);
                                } catch(IOException e) {
                                   System.err.println("Error creating temp image file " + filename);
                                   // The html viewer will show a broken image too to alert them
                                }
                             }
                          }
                       }
                       super.startElement(uri, localName, name, newAttrs);
                    } else {
                       super.startElement(uri, localName, name, atts);
                    }
                }
            }
            @Override
            public void endElement(String uri, String localName, String name)
                    throws SAXException {
                if (XHTMLContentHandler.XHTML.equals(uri)) {
                    uri = null;
                }
                if (!"head".equals(localName)) {
                    super.endElement(uri, localName, name);
                }
            }
            @Override
            public void startPrefixMapping(String prefix, String uri) {
            }
            @Override
            public void endPrefixMapping(String prefix) {
            }
        };
    }

    private ContentHandler getTextContentHandler(Writer writer) {
        return new BodyContentHandler(writer);
    }
    private ContentHandler getTextMainContentHandler(Writer writer) {
        return new BoilerpipeContentHandler(writer);
    }

    private ContentHandler getXmlContentHandler(Writer writer)
            throws TransformerConfigurationException {
        SAXTransformerFactory factory = (SAXTransformerFactory)
            SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
        handler.setResult(new StreamResult(writer));
        return handler;
    }

    /**
     * A {@link DocumentSelector} that accepts only images.
     */
    private static class ImageDocumentSelector implements DocumentSelector {
      public boolean select(Metadata metadata) {
         String type = metadata.get(Metadata.CONTENT_TYPE);
         return type != null && type.startsWith("image/");
      }
    }
    
    /**
     * A recursive parser that saves certain images into the temporary
     *  directory, and delegates everything else to another downstream
     *  parser.
     */
    private static class ImageSavingParser extends AbstractParser {
      private Map<String,File> wanted = new HashMap<String,File>();
      private Parser downstreamParser;
      private File tmpDir;
      
      private ImageSavingParser(Parser downstreamParser) {
         this.downstreamParser = downstreamParser;
         
         try {
            File t = File.createTempFile("tika", ".test");
            tmpDir = t.getParentFile();
         } catch(IOException e) {}
      }
      
      public File requestSave(String embeddedName) throws IOException {
         String suffix = ".tika";
         
         int splitAt = embeddedName.lastIndexOf('.');
         if (splitAt > 0) {
            embeddedName.substring(splitAt);
         }
         
         File tmp = File.createTempFile("tika-embedded-", suffix);
         wanted.put(embeddedName, tmp);
         return tmp;
      }
      
      public Set<MediaType> getSupportedTypes(ParseContext context) {
         // Never used in an auto setup
         return null;
      }

      public void parse(InputStream stream, ContentHandler handler,
            Metadata metadata, ParseContext context) throws IOException,
            SAXException, TikaException {
         String name = metadata.get(Metadata.RESOURCE_NAME_KEY);
         if(name != null && wanted.containsKey(name)) {
            FileOutputStream out = new FileOutputStream(wanted.get(name));
            IOUtils.copy(stream, out);
            out.close();
         } else {
            if(downstreamParser != null) {
               downstreamParser.parse(stream, handler, metadata, context);
            }
         }
      }
    }
}
