package com.yqboots.dict.web.support;

import com.yqboots.core.html.HtmlOption;
import com.yqboots.core.html.support.AbstractHtmlOptionsResolver;
import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.core.DataDictManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Resolves html option from data dictionaries.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Component
@Order // be the last one
public class DataDictHtmlOptionsResolver extends AbstractHtmlOptionsResolver {
    private final DataDictManager dataDictManager;

    @Autowired
    public DataDictHtmlOptionsResolver(final DataDictManager dataDictManager) {
        super();
        this.dataDictManager = dataDictManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final String name) {
        // this resolver is the last one, always check if exists in the data dict.
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HtmlOption> getHtmlOptions(final String name, final String... attributes) {
        List<HtmlOption> results = new ArrayList<>();

        List<DataDict> dataDicts = dataDictManager.getDataDicts(name);
        dataDicts.forEach(new DataDictConsumer(results));

        return results;
    }

    private static class DataDictConsumer implements Consumer<DataDict> {
        /**
         * The container of the data options.
         */
        private final List<HtmlOption> options;

        public DataDictConsumer(final List<HtmlOption> options) {
            this.options = options;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void accept(final DataDict dataDict) {
            HtmlOption options = new HtmlOption();
            options.setText(dataDict.getText());
            options.setValue(dataDict.getValue());
            this.options.add(options);
        }
    }
}
