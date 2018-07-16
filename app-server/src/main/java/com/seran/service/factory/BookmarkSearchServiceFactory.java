package com.seran.service.factory;

import com.seran.service.BookmarkSearchService;

public interface BookmarkSearchServiceFactory {
    BookmarkSearchService getBookmarkSearchService(String selector);
}
