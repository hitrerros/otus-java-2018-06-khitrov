package ru.otus.khitrov.template;

import freemarker.template.*;
import ru.otus.khitrov.base.dataSets.UserDataSet;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author v.chibrikov
 */
public class TemplateProcessor {
    private static final String HTML_DIR = "tml";

    private final Configuration configuration;

    public TemplateProcessor() throws IOException {

        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDirectoryForTemplateLoading(new File(HTML_DIR));
//        configuration.setObjectWrapper(new UserDataSetObjectWrapper(Configuration.VERSION_2_3_28));
        configuration.setObjectWrapper( new DefaultObjectWrapperBuilder( Configuration.VERSION_2_3_28).build() );
        configuration.setDefaultEncoding("UTF-8");
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
