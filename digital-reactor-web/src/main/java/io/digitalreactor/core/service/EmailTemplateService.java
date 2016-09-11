package io.digitalreactor.core.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.digitalreactor.model.TemplateMnemonicEnum;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by ingvard on 11.09.16.
 */
public class EmailTemplateService {
    private final String TEMPLATES_PATH = "/email";
    private final Configuration cfg;
    private final Map<TemplateMnemonicEnum, String> templateMnemonic;

    public EmailTemplateService(Map<TemplateMnemonicEnum, String> templateMnemonic) {
        this.templateMnemonic = templateMnemonic;
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(EmailTemplateService.class, TEMPLATES_PATH);
    }

    String prepare(TemplateMnemonicEnum templateName, Map arg) {
        StringWriter writer = new StringWriter();
        Template temp = null;
        //TODO[St.maxim] cleaning exception
        try {
            temp = cfg.getTemplate(templateMnemonic.get(templateName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            temp.process(arg, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
