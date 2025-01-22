package com.skn.froala.editor;

/**
 * Provides custom validation.
 *
 * @author florin@froala.com
 */
public interface CustomValidation {

	boolean validate(String filePath, String mimeType);
}
