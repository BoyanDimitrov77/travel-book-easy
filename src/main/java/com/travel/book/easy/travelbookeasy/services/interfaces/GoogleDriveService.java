package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface GoogleDriveService {

	String uplaodFile(InputStream inputStream, String usernameFolder, String fileId , String fileType) throws IOException;

	String getAccessTokenGD() throws IOException;
}
