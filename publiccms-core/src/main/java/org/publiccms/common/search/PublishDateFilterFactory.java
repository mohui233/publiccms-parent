package org.publiccms.common.search;

import java.util.Date;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.filter.impl.CachingWrapperFilter;

/**
 *
 * PublishDateFilterFactory
 * 
 */
@SuppressWarnings("deprecation")
public class PublishDateFilterFactory {
    private Date startPublishDate;
    private Date endPublishDate;

    /**
     * @return
     */
    @Factory
    public Filter getFilter() {
        Long start = null;
        Long end = null;
        if (null != startPublishDate) {
            start = startPublishDate.getTime();
        }
        if (null != endPublishDate) {
            end = endPublishDate.getTime();
        }
        Query query = NumericRangeQuery.newLongRange("publishDate", start, end, true, true);
        return new CachingWrapperFilter(new QueryWrapperFilter(query));
    }

    /**
     * @param endPublishDate
     */
    public void setEndPublishDate(Date endPublishDate) {
        this.endPublishDate = endPublishDate;
    }

    /**
     * @param startPublishDate
     *            the startPublishDate to set
     */
    public void setStartPublishDate(Date startPublishDate) {
        this.startPublishDate = startPublishDate;
    }
}