package pub.spring.support;

import org.springframework.web.multipart.MultipartFile;
import pub.types.NotImplementedException;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-12-7
 */
public class MultipartFileHolder implements MultipartFile, Serializable {

	private byte[] bytes;
	private String name;
	private String originalFilename;
	private String contentType;

	public MultipartFileHolder() {
		//
	}

	public MultipartFileHolder(MultipartFile file) {
		if(file == null || file.isEmpty()) {
			bytes = new byte[0];
			return;
		}
		try {
			bytes = file.getBytes();
			name = file.getName();
			originalFilename = file.getOriginalFilename();
			contentType = file.getContentType();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOriginalFilename() {
		return originalFilename;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public boolean isEmpty() {
		return bytes.length == 0;
	}

	@Override
	public long getSize() {
		return bytes.length;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(getBytes());
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		throw new NotImplementedException();
	}
}
