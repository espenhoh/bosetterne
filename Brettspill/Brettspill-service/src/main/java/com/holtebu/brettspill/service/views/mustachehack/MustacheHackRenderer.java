package com.holtebu.brettspill.service.views.mustachehack;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheException;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewRenderer;

import javax.ws.rs.WebApplicationException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;


/**
 * A {@link io.dropwizard.views.ViewRenderer} which renders Mustache ({@code .mustache}) templates without caching templates.
 */
public class MustacheHackRenderer implements ViewRenderer {

    @Override
    public boolean isRenderable(View view) {
        return view.getTemplateName().endsWith(getSuffix());
    }

    @Override
    public void render(View view, Locale locale, OutputStream output) throws IOException, WebApplicationException {
        try {
            //final Mustache template = factories.get(view.getClass()).compile(view.getTemplateName());
            final Mustache template = new PerClassMustacheFactory(view.getClass()).compile(view.getTemplateName());
            final Charset charset = view.getCharset().or(Charsets.UTF_8);
            try (OutputStreamWriter writer = new OutputStreamWriter(output, charset)) {
                template.execute(writer, view);
            }
        } catch (UncheckedExecutionException | MustacheException ignored) {
            throw new FileNotFoundException("Template " + view.getTemplateName() + " not found. Or " + ignored.getMessage());
        }
    }

    @Override
    public void configure(Map<String, String> options) {}

    @Override
    public String getSuffix() {
        return ".mustache";
    }


}

/**
 * A class-specific Mustache factory which caches the parsed/compiled templates.
 */
class PerClassMustacheFactory extends DefaultMustacheFactory {
    private final Class<? extends View> klass;

    PerClassMustacheFactory(Class<? extends View> klass) {
        this.klass = klass;
    }

    @Override
    public Reader getReader(String resourceName) {
        final InputStream is = klass.getResourceAsStream(resourceName);
        if (is == null) {
            throw new MustacheException("Template " + resourceName + " not found");
        }
        return new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
    }
}
