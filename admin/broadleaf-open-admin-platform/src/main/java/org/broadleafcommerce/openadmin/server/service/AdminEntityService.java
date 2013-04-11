/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.openadmin.client.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.client.dto.Entity;
import org.broadleafcommerce.openadmin.client.dto.FilterAndSortCriteria;
import org.broadleafcommerce.openadmin.client.dto.Property;
import org.broadleafcommerce.openadmin.server.domain.PersistencePackageRequest;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;

import com.gwtincubator.security.exception.ApplicationSecurityException;

import java.util.Map;

/**
 * @author Andre Azzolini (apazzolini)
 */
public interface AdminEntityService {

    /**
     * Returns class metadata for the given request object
     * 
     * @param request
     * @return ClassMetadata for the given request
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public ClassMetadata getClassMetadata(PersistencePackageRequest request)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Returns an Entity[] representing the records that were found for the given request.
     * 
     * @param request
     * @return the Entity[]
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public Entity[] getRecords(PersistencePackageRequest request)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Returns a specific record for the given request and primary key id
     * 
     * @param request
     * @param id
     * @return the Entity
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public Entity getRecord(PersistencePackageRequest request, String id)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Persists the given entity
     * 
     * @param entityForm
     * @param customCriteria
     * @return the persisted Entity
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public Entity addEntity(EntityForm entityForm, String[] customCriteria)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Updates the given entity
     * 
     * @param entityForm
     * @param customCriteria
     * @return the persisted Entity
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public Entity updateEntity(EntityForm entityForm, String[] customCriteria)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Removes the given entity
     * 
     * @param entityForm
     * @param customCriteria
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public void removeEntity(EntityForm entityForm, String[] customCriteria)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Gets an Entity representing a specific collection item
     * 
     * @param containingClassMetadata
     * @param containingEntity
     * @param collectionProperty
     * @param collectionItemId
     * @return the Entity
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public Entity getAdvancedCollectionRecord(ClassMetadata containingClassMetadata, Entity containingEntity,
            Property collectionProperty, String collectionItemId)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Returns the Entity[] representing the records that belong to the specified collectionProperty for the 
     * given containingClass and the primary key for the containingClass
     * 
     * @param containingClassMetadata
     * @param containingEntity
     * @param collectionProperty
     * @return the Entity[]
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public Entity[] getRecordsForCollection(ClassMetadata containingClassMetadata, Entity containingEntity,
            Property collectionProperty, FilterAndSortCriteria[] criteria)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Returns all records for all subcollections of the specified request and its primary key
     * 
     * @param ppr
     * @param containingEntity
     * @return all Entity[] for all collections for the specified containingClass
     * @throws ServiceException
     * @throws ApplicationSecurityException
     * 
     * @see #getRecordsForCollection(ClassMetadata, String, Property)
     */
    public Map<String, Entity[]> getRecordsForAllSubCollections(PersistencePackageRequest ppr, Entity containingEntity)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Adds an item into the specified collection
     * 
     * @param entityForm
     * @param mainMetadata
     * @param field
     * @param parentEntity
     * @return the persisted Entity
     * @throws ServiceException
     * @throws ApplicationSecurityException
     * @throws ClassNotFoundException
     */
    public Entity addSubCollectionEntity(EntityForm entityForm, ClassMetadata mainMetadata, Property field, 
            Entity parentEntity)
            throws ServiceException, ApplicationSecurityException, ClassNotFoundException;

    /**
     * Updates the specified collection item
     * 
     * @param entityForm
     * @param mainMetadata
     * @param field
     * @param parentEntity
     * @param collectionItemId
     * @return the persisted Entity
     * @throws ServiceException
     * @throws ApplicationSecurityException
     * @throws ClassNotFoundException
     */
    public Entity updateSubCollectionEntity(EntityForm entityForm, ClassMetadata mainMetadata, Property field,
            Entity parentEntity, String collectionItemId)
            throws ServiceException, ApplicationSecurityException, ClassNotFoundException;

    /**
     * Removes the given item from the specified collection.
     * 
     * @param mainMetadata
     * @param field
     * @param parentId
     * @param itemId
     * @param priorKey - only needed for Map type collections
     * @throws ServiceException
     * @throws ApplicationSecurityException
     */
    public void removeSubCollectionEntity(ClassMetadata mainMetadata, Property field, Entity parentEntity, String itemId,
            String priorKey)
            throws ServiceException, ApplicationSecurityException;

    /**
     * Returns the appropriate id to use for the given entity/metadata and prefix when dealing with collections. For
     * example, on the Product screen, we display associated media. However, this media is actually owned by the Sku entity,
     * which means its property name is "defaultSku.skuMedia". In this case, when wanting to look up media for this product,
     * we cannot use the id of the product. Instead, we need to use the id of the sku.
     * 
     * @param cmd
     * @param entity
     * @param propertyName
     * @return the id to be used for this relationship
     */
    public String getContextSpecificRelationshipId(ClassMetadata cmd, Entity entity, String propertyName);

}