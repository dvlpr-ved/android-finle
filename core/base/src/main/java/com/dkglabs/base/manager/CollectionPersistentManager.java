package com.dkglabs.base.manager;

import com.dkglabs.base.utils.DataUtil;
import com.dkglabs.model.CollectionCard;

public class CollectionPersistentManager extends BasePersistentManager {

    public static CollectionCard getCollectionCard() {
        String respString = (String) readFromPreference("collection_card", String.class, null);
        CollectionCard response = DataUtil.getObjectFromJson(respString, CollectionCard.class);
        return response;
    }

    public static void setCollectionCard(CollectionCard collectionCard) {
        String respString = null;
        if (collectionCard != null) {
            respString = DataUtil.getStringFromObj(collectionCard);
        }
        writeToPreference("collection_card", respString);
    }

}
