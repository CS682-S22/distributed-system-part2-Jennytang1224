// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: protos/peerInfo.proto

package dsd.pubsub.protos;

public final class PeerInfo {
  private PeerInfo() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PeerOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Peer)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * producer or consumer
     * </pre>
     *
     * <code>string type = 1;</code>
     * @return The type.
     */
    java.lang.String getType();
    /**
     * <pre>
     * producer or consumer
     * </pre>
     *
     * <code>string type = 1;</code>
     * @return The bytes for type.
     */
    com.google.protobuf.ByteString
        getTypeBytes();

    /**
     * <code>string hostName = 2;</code>
     * @return The hostName.
     */
    java.lang.String getHostName();
    /**
     * <code>string hostName = 2;</code>
     * @return The bytes for hostName.
     */
    com.google.protobuf.ByteString
        getHostNameBytes();

    /**
     * <code>int32 portNumber = 3;</code>
     * @return The portNumber.
     */
    int getPortNumber();
  }
  /**
   * Protobuf type {@code Peer}
   */
  public static final class Peer extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Peer)
      PeerOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Peer.newBuilder() to construct.
    private Peer(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Peer() {
      type_ = "";
      hostName_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Peer();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Peer(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              type_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              hostName_ = s;
              break;
            }
            case 24: {

              portNumber_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return dsd.pubsub.protos.PeerInfo.internal_static_Peer_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return dsd.pubsub.protos.PeerInfo.internal_static_Peer_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              dsd.pubsub.protos.PeerInfo.Peer.class, dsd.pubsub.protos.PeerInfo.Peer.Builder.class);
    }

    public static final int TYPE_FIELD_NUMBER = 1;
    private volatile java.lang.Object type_;
    /**
     * <pre>
     * producer or consumer
     * </pre>
     *
     * <code>string type = 1;</code>
     * @return The type.
     */
    @java.lang.Override
    public java.lang.String getType() {
      java.lang.Object ref = type_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        type_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * producer or consumer
     * </pre>
     *
     * <code>string type = 1;</code>
     * @return The bytes for type.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getTypeBytes() {
      java.lang.Object ref = type_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        type_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int HOSTNAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object hostName_;
    /**
     * <code>string hostName = 2;</code>
     * @return The hostName.
     */
    @java.lang.Override
    public java.lang.String getHostName() {
      java.lang.Object ref = hostName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        hostName_ = s;
        return s;
      }
    }
    /**
     * <code>string hostName = 2;</code>
     * @return The bytes for hostName.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getHostNameBytes() {
      java.lang.Object ref = hostName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        hostName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PORTNUMBER_FIELD_NUMBER = 3;
    private int portNumber_;
    /**
     * <code>int32 portNumber = 3;</code>
     * @return The portNumber.
     */
    @java.lang.Override
    public int getPortNumber() {
      return portNumber_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(type_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, type_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(hostName_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, hostName_);
      }
      if (portNumber_ != 0) {
        output.writeInt32(3, portNumber_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(type_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, type_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(hostName_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, hostName_);
      }
      if (portNumber_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, portNumber_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof dsd.pubsub.protos.PeerInfo.Peer)) {
        return super.equals(obj);
      }
      dsd.pubsub.protos.PeerInfo.Peer other = (dsd.pubsub.protos.PeerInfo.Peer) obj;

      if (!getType()
          .equals(other.getType())) return false;
      if (!getHostName()
          .equals(other.getHostName())) return false;
      if (getPortNumber()
          != other.getPortNumber()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + getType().hashCode();
      hash = (37 * hash) + HOSTNAME_FIELD_NUMBER;
      hash = (53 * hash) + getHostName().hashCode();
      hash = (37 * hash) + PORTNUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getPortNumber();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static dsd.pubsub.protos.PeerInfo.Peer parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(dsd.pubsub.protos.PeerInfo.Peer prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Peer}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Peer)
        dsd.pubsub.protos.PeerInfo.PeerOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return dsd.pubsub.protos.PeerInfo.internal_static_Peer_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return dsd.pubsub.protos.PeerInfo.internal_static_Peer_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                dsd.pubsub.protos.PeerInfo.Peer.class, dsd.pubsub.protos.PeerInfo.Peer.Builder.class);
      }

      // Construct using dsd.pubsub.protos.PeerInfo.Peer.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        type_ = "";

        hostName_ = "";

        portNumber_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return dsd.pubsub.protos.PeerInfo.internal_static_Peer_descriptor;
      }

      @java.lang.Override
      public dsd.pubsub.protos.PeerInfo.Peer getDefaultInstanceForType() {
        return dsd.pubsub.protos.PeerInfo.Peer.getDefaultInstance();
      }

      @java.lang.Override
      public dsd.pubsub.protos.PeerInfo.Peer build() {
        dsd.pubsub.protos.PeerInfo.Peer result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public dsd.pubsub.protos.PeerInfo.Peer buildPartial() {
        dsd.pubsub.protos.PeerInfo.Peer result = new dsd.pubsub.protos.PeerInfo.Peer(this);
        result.type_ = type_;
        result.hostName_ = hostName_;
        result.portNumber_ = portNumber_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof dsd.pubsub.protos.PeerInfo.Peer) {
          return mergeFrom((dsd.pubsub.protos.PeerInfo.Peer)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(dsd.pubsub.protos.PeerInfo.Peer other) {
        if (other == dsd.pubsub.protos.PeerInfo.Peer.getDefaultInstance()) return this;
        if (!other.getType().isEmpty()) {
          type_ = other.type_;
          onChanged();
        }
        if (!other.getHostName().isEmpty()) {
          hostName_ = other.hostName_;
          onChanged();
        }
        if (other.getPortNumber() != 0) {
          setPortNumber(other.getPortNumber());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        dsd.pubsub.protos.PeerInfo.Peer parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (dsd.pubsub.protos.PeerInfo.Peer) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object type_ = "";
      /**
       * <pre>
       * producer or consumer
       * </pre>
       *
       * <code>string type = 1;</code>
       * @return The type.
       */
      public java.lang.String getType() {
        java.lang.Object ref = type_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          type_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * producer or consumer
       * </pre>
       *
       * <code>string type = 1;</code>
       * @return The bytes for type.
       */
      public com.google.protobuf.ByteString
          getTypeBytes() {
        java.lang.Object ref = type_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          type_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * producer or consumer
       * </pre>
       *
       * <code>string type = 1;</code>
       * @param value The type to set.
       * @return This builder for chaining.
       */
      public Builder setType(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * producer or consumer
       * </pre>
       *
       * <code>string type = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearType() {
        
        type_ = getDefaultInstance().getType();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * producer or consumer
       * </pre>
       *
       * <code>string type = 1;</code>
       * @param value The bytes for type to set.
       * @return This builder for chaining.
       */
      public Builder setTypeBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        type_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object hostName_ = "";
      /**
       * <code>string hostName = 2;</code>
       * @return The hostName.
       */
      public java.lang.String getHostName() {
        java.lang.Object ref = hostName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          hostName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string hostName = 2;</code>
       * @return The bytes for hostName.
       */
      public com.google.protobuf.ByteString
          getHostNameBytes() {
        java.lang.Object ref = hostName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          hostName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string hostName = 2;</code>
       * @param value The hostName to set.
       * @return This builder for chaining.
       */
      public Builder setHostName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        hostName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string hostName = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearHostName() {
        
        hostName_ = getDefaultInstance().getHostName();
        onChanged();
        return this;
      }
      /**
       * <code>string hostName = 2;</code>
       * @param value The bytes for hostName to set.
       * @return This builder for chaining.
       */
      public Builder setHostNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        hostName_ = value;
        onChanged();
        return this;
      }

      private int portNumber_ ;
      /**
       * <code>int32 portNumber = 3;</code>
       * @return The portNumber.
       */
      @java.lang.Override
      public int getPortNumber() {
        return portNumber_;
      }
      /**
       * <code>int32 portNumber = 3;</code>
       * @param value The portNumber to set.
       * @return This builder for chaining.
       */
      public Builder setPortNumber(int value) {
        
        portNumber_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 portNumber = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearPortNumber() {
        
        portNumber_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Peer)
    }

    // @@protoc_insertion_point(class_scope:Peer)
    private static final dsd.pubsub.protos.PeerInfo.Peer DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new dsd.pubsub.protos.PeerInfo.Peer();
    }

    public static dsd.pubsub.protos.PeerInfo.Peer getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Peer>
        PARSER = new com.google.protobuf.AbstractParser<Peer>() {
      @java.lang.Override
      public Peer parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Peer(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Peer> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Peer> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public dsd.pubsub.protos.PeerInfo.Peer getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Peer_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Peer_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025protos/peerInfo.proto\":\n\004Peer\022\014\n\004type\030" +
      "\001 \001(\t\022\020\n\010hostName\030\002 \001(\t\022\022\n\nportNumber\030\003 " +
      "\001(\005B\035\n\021dsd.pubsub.protosB\010PeerInfob\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Peer_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Peer_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Peer_descriptor,
        new java.lang.String[] { "Type", "HostName", "PortNumber", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
