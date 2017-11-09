package com.lognex.api.converter.base;

import com.fasterxml.jackson.core.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class CustomJsonGenerator extends JsonGenerator {

    private JsonGenerator delegate;

    public CustomJsonGenerator(JsonGenerator generator) {
        this.delegate = generator;
        delegate.disable(Feature.ESCAPE_NON_ASCII);
    }

    public void writeStringFieldIfNotEmpty(String fieldName, String value) throws IOException {
        if (value != null && !value.isEmpty()) {
            delegate.writeStringField(fieldName, value);
        }
    }

    public void writeIntegerFieldIfNotNull(String fieldName, Integer value) throws IOException {
        if (value != null) {
            delegate.writeNumberField(fieldName, value);
        }
    }

    // DELEGATE
    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        delegate = delegate.setCodec(objectCodec);
        return this;
    }

    @Override
    public ObjectCodec getCodec() {
        return delegate.getCodec();
    }

    @Override
    public Version version() {
        return delegate.version();
    }

    @Override
    public JsonGenerator enable(Feature feature) {
        delegate = delegate.enable(feature);
        return this;
    }

    @Override
    public JsonGenerator disable(Feature feature) {
        delegate = delegate.disable(feature);
        return this;
    }

    @Override
    public boolean isEnabled(Feature feature) {
        return delegate.isEnabled(feature);
    }

    @Override
    public int getFeatureMask() {
        return delegate.getFeatureMask();
    }

    @Override
    public JsonGenerator setHighestNonEscapedChar(int charCode) {
        delegate = delegate.setHighestNonEscapedChar(charCode);
        return this;
    }


    @Override
    public JsonGenerator setFeatureMask(int i) {
        delegate = delegate.setFeatureMask(i);
        return this;

    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        delegate = delegate.useDefaultPrettyPrinter();
        return this;
    }

    @Override
    public void writeStartArray() throws IOException {
        delegate.writeStartArray();
    }

    @Override
    public void writeEndArray() throws IOException {
        delegate.writeEndArray();
    }

    @Override
    public void writeStartObject() throws IOException {
        delegate.writeStartObject();
    }

    @Override
    public void writeEndObject() throws IOException {
        delegate.writeEndObject();
    }

    @Override
    public void writeFieldName(String s) throws IOException {
        delegate.writeFieldName(s);
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        delegate.writeFieldName(serializableString);
    }

    @Override
    public void writeString(String s) throws IOException {
        delegate.writeString(s);
    }

    @Override
    public void writeString(char[] chars, int i, int i1) throws IOException {
        delegate.writeString(chars, i, i1);
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException {
        delegate.writeString(serializableString);
    }

    @Override
    public void writeRawUTF8String(byte[] bytes, int i, int i1) throws IOException {
        delegate.writeRawUTF8String(bytes, i, i1);
    }

    @Override
    public void writeUTF8String(byte[] bytes, int i, int i1) throws IOException {
        delegate.writeUTF8String(bytes, i , i1);
    }

    @Override
    public void writeRaw(String s) throws IOException {
        delegate.writeRaw(s);
    }

    @Override
    public void writeRaw(String s, int i, int i1) throws IOException {
        delegate.writeRaw(s, i, i1);
    }

    @Override
    public void writeRaw(char[] chars, int i, int i1) throws IOException {
        delegate.writeRaw(chars, i, i1);
    }

    @Override
    public void writeRaw(char c) throws IOException {
        delegate.writeRaw(c);
    }

    @Override
    public void writeRawValue(String s) throws IOException {
        delegate.writeRawValue(s);
    }

    @Override
    public void writeRawValue(String s, int i, int i1) throws IOException {
        delegate.writeRawValue(s, i, i1);
    }

    @Override
    public void writeRawValue(char[] chars, int i, int i1) throws IOException {
        delegate.writeRawValue(chars, i, i1);
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] bytes, int i, int i1) throws IOException {
        delegate.writeBinary(base64Variant, bytes, i, i1);
    }

    @Override
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
        return delegate.writeBinary(base64Variant, inputStream, i);
    }

    @Override
    public void writeNumber(int i) throws IOException {
        delegate.writeNumber(i);
    }

    @Override
    public void writeNumber(long l) throws IOException {
        delegate.writeNumber(l);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
        delegate.writeNumber(bigInteger);
    }

    @Override
    public void writeNumber(double v) throws IOException {
        delegate.writeNumber(v);
    }

    @Override
    public void writeNumber(float v) throws IOException {
        delegate.writeNumber(v);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        delegate.writeNumber(bigDecimal);
    }

    @Override
    public void writeNumber(String s) throws IOException {
        delegate.writeNumber(s);
    }

    @Override
    public void writeBoolean(boolean b) throws IOException {
        delegate.writeBoolean(b);
    }

    @Override
    public void writeNull() throws IOException {
        delegate.writeNull();
    }

    @Override
    public void writeObject(Object o) throws IOException {
        delegate.writeObject(o);
    }

    @Override
    public void writeTree(TreeNode treeNode) throws IOException {
        delegate.writeTree(treeNode);
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return delegate.getOutputContext();
    }

    @Override
    public void flush() throws IOException {
        delegate.flush();
    }

    @Override
    public boolean isClosed() {
        return delegate.isClosed();
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}
