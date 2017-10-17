package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lognex.api.model.base.field.FileRef;
import com.lognex.api.model.base.field.Meta;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.base.AbstractEntityLegendable;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.*;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.attribute.AttributeType;
import com.lognex.api.model.entity.attribute.AttributeValue;
import com.lognex.api.model.entity.good.Bundle;
import com.lognex.api.model.entity.good.Product;
import com.lognex.api.model.entity.good.Service;
import com.lognex.api.model.entity.good.Variant;
import com.lognex.api.util.DateUtils;
import com.lognex.api.util.ID;
import com.lognex.api.util.MetaHrefUtils;
import com.lognex.api.util.Type;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;


public class AttributesConverter implements CustomFieldsConverter<IEntityWithAttributes>{

    @Override
    public void fromJson(JsonNode node, IEntityWithAttributes entity) {
        if (node.has("attributes")){
            ArrayNode attributesArray = (ArrayNode) node.get("attributes");
            if (attributesArray.size() > 0){
                for (int i = 0; i < attributesArray.size(); ++i){
                    ObjectNode attribute = (ObjectNode) attributesArray.get(i);
                    String id = attribute.get("id").asText();
                    String name = attribute.get("name").asText();
                    String type = attribute.get("type").asText();
                    switch (AttributeType.find(type)){
                        case TEXT:
                        case LINK:
                        case STRING: {
                            String val = attribute.get("value").asText();
                            entity.getAttributes().add(new Attribute<>(id, name, type, new AttributeValue<>(val)));
                            break;
                        }
                        case LONG: {
                            long val = attribute.get("value").asLong();
                            entity.getAttributes().add(new Attribute<>(id, name, type, new AttributeValue<>(val)));
                            break;
                        }
                        case DOUBLE: {
                            double val = attribute.get("value").asDouble();
                            entity.getAttributes().add(new Attribute<>(id, name, type, new AttributeValue<>(val)));
                            break;
                        }
                        case TIME:{
                            Date date = DateUtils.parseDate(attribute.get("value").asText());
                            entity.getAttributes().add(new Attribute<>(id, name, type, new AttributeValue<>(date)));
                            break;
                        }
                        case BOOLEAN:{
                            boolean value = attribute.get("value").asBoolean();
                            entity.getAttributes().add(new Attribute<>(id, name, type, new AttributeValue<>(value)));
                            break;
                        }
                        case FILE:{
                            String href = attribute.get("download").get("href").asText();
                            String mediaType = attribute.get("download").get("mediaType").asText();
                            entity.getAttributes().add(new Attribute<>(id, name, type, new AttributeValue<>(new FileRef(href, mediaType))));
                            break;
                        }
                        default: {
                            AttributeValue<? extends AbstractEntity> entityValue = parseEntity(attribute, AttributeType.find(type));
                            entity.getAttributes().add(new Attribute<>(id, name, type, entityValue));
                        }
                    }
                }
            }
        }
    }

    private AttributeValue<? extends AbstractEntity> parseEntity(JsonNode node, AttributeType type){
        ID id = MetaHrefUtils.getId(node.get("value").get("meta").get("href").asText());
        String name = node.get("value").get("name").asText();
        if (AttributeType.EMBEDDED_ENTITIES.contains(type)){
            switch (type){
                case COUNTERPARTY:
                    return new AttributeValue<>(fillEntityValue(new Counterparty(), id, name));
                case PRODUCT:
                    return new AttributeValue<>(fillEntityValue(new Product(), id, name));
                case SERVICE:
                    return new AttributeValue<>(fillEntityValue(new Service(), id, name));
                case CONSIGNMENT:
                    return new AttributeValue<>(fillEntityValue(new Consignment(), id, name));
                case VARIANT:
                    return new AttributeValue<>(fillEntityValue(new Variant(), id, name));
                case BUNDLE:
                    return new AttributeValue<>(fillEntityValue(new Bundle(), id, name));
                case STORE:
                    return new AttributeValue<>(fillEntityValue(new Store(), id, name));
                case PROJECT:
                    return new AttributeValue<>(fillEntityValue(new Project(), id, name));
                case CONTRACT:
                    return new AttributeValue<>(fillEntityValue(new Contract(), id, name));
                case EMPLOYEE:
                    return new AttributeValue<>(fillEntityValue(new Employee(), id, name));
                default:{
                    throw new IllegalArgumentException("Unknown entity type: " + type);
                }
            }
        } else {
            return new AttributeValue<>(fillEntityValue(new CustomEntity(), id, name));
        }
    }

    private <T extends AbstractEntityLegendable> T fillEntityValue(T entity, ID id, String name){
        entity.setId(id);
        entity.setName(name);
        return entity;
    }

    @Override
    public void toJson(CustomJsonGenerator jgen, IEntityWithAttributes entity) throws IOException {
        if (entity.getAttributes() != null && entity.getAttributes().size() > 0){
            jgen.writeArrayFieldStart("attributes");
            Set<Attribute<?>> attributes = entity.getAttributes().
                    stream().
                    filter(a-> !a.getType().equals(AttributeType.FILE.name().toLowerCase())).collect(Collectors.toSet());
            for (Attribute<?> attribute : attributes){
                jgen.writeStartObject();
                jgen.writeStringFieldIfNotEmpty("id", attribute.getId());
                jgen.writeStringFieldIfNotEmpty("type", attribute.getType());
                AttributeType type = AttributeType.find(attribute.getType());
                switch (type){
                    case TEXT:
                    case LINK:
                    case STRING: {
                        jgen.writeStringFieldIfNotEmpty("value", (String) attribute.getValue().getValue());
                        break;
                    }
                    case LONG: {
                        jgen.writeNumberField("value", (Long) attribute.getValue().getValue());
                        break;
                    }
                    case DOUBLE: {
                        jgen.writeNumberField("value", (Double) attribute.getValue().getValue());
                        break;
                    }
                    case TIME:{
                        jgen.writeObjectField("value", attribute.getValue().getValue());
                        break;
                    }
                    case BOOLEAN:{
                        jgen.writeBooleanField("value", (Boolean) attribute.getValue().getValue());
                        break;
                    }
                    default: {
                        serializeEntityValue(jgen, attribute);
                    }
                }
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
    }

    private void serializeEntityValue(CustomJsonGenerator jgen, Attribute<?> attribute) throws IOException{
        jgen.writeObjectFieldStart("value");
        Type type = Type.find(((AbstractEntity)attribute.getValue().getValue()).getClass());
        if (type.equals(Type.CUSTOM_ENTITY)){
            if (((AbstractEntity) attribute.getValue().getValue()).getId() != null) {
                jgen.writeObjectField("meta", new Meta<>(type, attribute.getValue().getValue()));
            }
            jgen.writeStringFieldIfNotEmpty("name", ((CustomEntity)attribute.getValue().getValue()).getName());
        } else {
            jgen.writeObjectField("meta", new Meta<>(type, attribute.getValue().getValue()));
        }
        jgen.writeEndObject();
    }
}
