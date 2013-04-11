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

package org.broadleafcommerce.cms.page.domain;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.openadmin.audit.AdminAuditable;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Created by bpolster.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAGE_FLD")
@EntityListeners(value = { AdminAuditableListener.class })
public class PageFieldImpl implements PageField {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PageFieldId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "PageFieldId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "PageFieldImpl", allocationSize = 10)
    @Column(name = "PAGE_FLD_ID")
    protected Long id;

    @Embedded
    @AdminPresentation(excluded = true)
    protected AdminAuditable auditable = new AdminAuditable();

    @Column (name = "FLD_KEY")
    protected String fieldKey;

    @ManyToOne(targetEntity = PageImpl.class)
    @JoinColumn(name="PAGE_ID")
    protected Page page;

    @Column (name = "VALUE")
    protected String stringValue;

    @Column(name = "LOB_VALUE", length = Integer.MAX_VALUE)
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    protected String lobValue;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    @Override
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String getValue() {
        if (stringValue != null && stringValue.length() > 0) {
            return stringValue;
        } else {
            return lobValue;
        }
    }

    @Override
    public void setValue(String value) {
        if (value != null) {
            if (value.length() <= 256) {
                stringValue = value;
                lobValue = null;
            } else {
                stringValue = null;
                lobValue = value;
            }
        } else {
            lobValue = null;
            stringValue = null;
        }
    }

    @Override
    public AdminAuditable getAuditable() {
        return auditable;
    }

    @Override
    public void setAuditable(AdminAuditable auditable) {
        this.auditable = auditable;
    }

    @Override
    public PageField cloneEntity() {
        PageFieldImpl newPageField = new PageFieldImpl();
        newPageField.fieldKey = fieldKey;
        newPageField.page = page;
        newPageField.lobValue = lobValue;
        newPageField.stringValue = stringValue;
        return newPageField;
    }
}

