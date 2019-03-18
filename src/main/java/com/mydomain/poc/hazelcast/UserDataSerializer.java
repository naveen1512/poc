package com.mydomain.poc.hazelcast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.mydomain.poc.hazelcast.model.UserData;

public class UserDataSerializer implements StreamSerializer<UserData> {

	private boolean compress = true;

	private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {

		@Override
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			kryo.register(UserData.class);
			return kryo;
		}
	};

	public UserDataSerializer() {
	
	}
	
	public UserDataSerializer(boolean compress) {
		this.compress = compress;
	}

	@Override
	public int getTypeId() {
		return 1;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void write(ObjectDataOutput objectDataOutput, UserData userData) throws IOException {
		Kryo kryo = kryoThreadLocal.get();

		if (this.compress) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16384);
			DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
			Output output = new Output(deflaterOutputStream);

			kryo.writeObject(output, userData);
			output.close();

			byte[] bytes = byteArrayOutputStream.toByteArray();
			objectDataOutput.write(bytes);
		} else {
			Output output = new Output((OutputStream) objectDataOutput);
			kryo.writeObject(output, userData);
			output.flush();
		}
	}

	@Override
	public UserData read(ObjectDataInput objectDataInput) throws IOException {
		InputStream in = (InputStream) objectDataInput;

		if (compress) {
			in = new InflaterInputStream(in);
		}

		Input input = new Input(in);
		Kryo kryo = kryoThreadLocal.get();
		return kryo.readObject(input, UserData.class);
	}

}
