/************************************************************************
 * Copyright © 2007-2016 - General Electric Company, All Rights Reserved
 *
 * Project: SADL
 *
 * Description: The Semantic Application Design Language (SADL) is a
 * language for building semantic models and expressing rules that
 * capture additional domain knowledge. The SADL-IDE (integrated
 * development environment) is a set of Eclipse plug-ins that
 * support the editing and testing of semantic models using the
 * SADL language.
 *
 * This software is distributed "AS-IS" without ANY WARRANTIES
 * and licensed under the Eclipse Public License - v 1.0
 * which is available at http://www.eclipse.org/org/documents/epl-v10.php
 *
 ***********************************************************************/
package org.xtuml.bp.xtext.generator

import com.google.common.io.ByteStreams
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.apache.log4j.Logger

class JFlexLoader {
	
	final static Logger log = Logger.getLogger(typeof(JFlexLoader))
	static final String DOWNLOAD_URL = "http://jflex.de/jflex-1.4.3.zip"
	static final String MAIN_CLASS = "JFlex.Main"
	ClassLoader loader = JFlexLoader.getClassLoader
	String downloadTo = "./.jflex.jar"
	boolean askBeforeDownload = false

	def ClassLoader getClassLoader() {
		return loader
	}

	def void runJFlex(String... args) {
		try {
			var Class<?> main = loader.loadClass(MAIN_CLASS)
			var Method mainMethod = main.getMethod("main", typeof(String[]))
			mainMethod.invoke(null, (#[args] as Object[]))
		} catch (Exception e) {
			throw new RuntimeException(e)
		}
	}

	def void setDownloadPath(String downloadTo) {
		this.downloadTo = downloadTo
	}

	def void setAskBeforeDownload(boolean askBeforeDownload) {
		this.askBeforeDownload = askBeforeDownload
	}

	def String getDownloadTo() {
		return downloadTo
	}

	def File getJarFile() {
		return new File(downloadTo)
	}

	def void preInvoke() {
		try {
			loader.loadClass(MAIN_CLASS)
		} catch (ClassNotFoundException e) {
			var File jarFile = getJarFile()
			if (!jarFile.exists()) {
				try {
					if(!download(jarFile)) return;
				} catch (IOException ioe) {
					throw new RuntimeException(ioe)
				}
			}
			if (jarFile.exists()) {
				try {
					var URL url = jarFile.toURI().toURL()
					loader = new URLClassLoader((#[url] as URL[]), loader)
					var ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader()
					try {
						Thread::currentThread().setContextClassLoader(loader)
						loader.loadClass(MAIN_CLASS)
					} finally {
						Thread::currentThread().setContextClassLoader(contextClassLoader)
					}
				} catch (Exception e1) {
					throw new RuntimeException(e)
				}
			}
		}
	}

	def private boolean download(File jarFile) throws IOException {
		val File tempFile = File.createTempFile("JFlex", "zip")
		tempFile.deleteOnExit()
		if (askBeforeDownload) {
			var boolean ok = false
			while (!ok) {
				System::err.print("Download JFlex 1.4.3? (type 'y' or 'n' and hit enter)")
				var int read = System::in.read()
				if (read === Character.valueOf('n').charValue) {
					return false
				} else if (read === Character.valueOf('y').charValue) {
					ok = true
				}
			}
		}
		log.info('''downloading JFlex 1.4.3 from '«»«DOWNLOAD_URL»' ...'''.toString)
		copyIntoFileAndCloseStream(new URL(DOWNLOAD_URL).openStream(), tempFile)
		log.info('''finished downloading. Now extracting to «downloadTo»'''.toString)
		val ZipFile zipFile = new ZipFile(tempFile)
		try {
			val ZipEntry entry = zipFile.getEntry("jflex/lib/JFlex.jar")
			copyIntoFileAndCloseStream(zipFile.getInputStream(entry), jarFile)
		} finally {
			zipFile.close()
		}
		return true
	}

	def protected void copyIntoFileAndCloseStream(InputStream content,
		File file) throws FileNotFoundException, IOException {
		var BufferedInputStream inputStream = new BufferedInputStream(content)
		try {
			var BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))
			try {
				ByteStreams::copy(inputStream, outputStream)
			} finally {
				outputStream.close()
			}
		} finally {
			inputStream.close()
		}
	}

}
