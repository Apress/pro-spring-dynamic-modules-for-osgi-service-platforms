/*
 * Copyright 2006-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apress.springosgi.ch9.testsosgi.testivyprovisions;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.DefaultDocumentLoader;
import org.springframework.beans.factory.xml.DocumentLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.osgi.test.provisioning.ArtifactLocator;
import org.springframework.util.xml.DomUtils;
import org.springframework.util.xml.XmlValidationModeDetector;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Locator for artifacts found in the local ivy repository. Does <strong>not</strong>
 * use Maven libraries, it rather uses the ivy patterns and conventions to
 * identify the artifacts.
 * Class is derived from the LocalFileSystemMavenRepository included in Spring-DM's
 * distribution
 *
 * @author Daniel Rubio
 * 
 */
public class LocalFileSystemIvyRepository implements ArtifactLocator {

	private static final char SLASH_CHAR = '/';

	private static final String LOCAL_REPOSITORY_ELEM = "localRepository";

	private static final Log log = LogFactory.getLog(LocalFileSystemIvyRepository.class);

	/** local repo system property */
	private static final String SYS_PROPERTY = LOCAL_REPOSITORY_ELEM;
	/** user home system property */
	private static final String USER_HOME_PROPERTY = "user.home";
	/** ivy2 local user settings */
	private static final String IVY2_DIR = ".ivy2";
	/** default local repository */
	private static final String DEFAULT_DIR = IVY2_DIR.concat("/cache");
	/** discovered local ivy2 repository home */
	private String repositoryHome;


	/**
	 * Initialization method It determines the repository path by checking the
	 * existence of <code>localRepository</code> system property and falling
	 * back to the default <code>user.home/.ivy2/cache</code>.
	 * 
	 * <p/> This method is used to postpone initialization until an artifact is
	 * actually located. As the test class is instantiated on each test run, the
	 * init() method prevents repetitive, waste-less initialization.
	 * 
	 */
	private void init() {
		// already discovered a repository home, bailing out
		if (repositoryHome != null)
			return;

		boolean trace = log.isDebugEnabled();

		final String[] sysProperties = new String[2];
		// check system property
		AccessController.doPrivileged(new PrivilegedAction() {

			public Object run() {
				sysProperties[0] = System.getProperty(SYS_PROPERTY);
				sysProperties[1] = System.getProperty(USER_HOME_PROPERTY);
				return null;
			}
		});
		String localRepository = sysProperties[0];
		String userHome = sysProperties[1];

		if (trace)
			log.trace("IVY2 system property [" + SYS_PROPERTY + "] has value=" + localRepository);

		if (localRepository == null) {
		    // fall back to the default location
		    localRepository = new File(userHome, DEFAULT_DIR).getAbsolutePath();
		    if (trace)
			log.trace("No custom setting found; using default M2 local repository=" + localRepository);
		    
		}
		
		repositoryHome = localRepository;
		log.info("Local Ivy2 repository used: [" + repositoryHome + "]");
	}

	/**
	 * Find a local ivy artifact. First tries to find the resource as a
	 * packaged artifact produced by a local maven build, and if that fails will
	 * search the local ivy repository.
	 * NOTE: This method forms part of the ArtifactLocator interface
	 * 
	 * @param groupId - the groupId of the organization supplying the bundle
	 * @param artifactId - the artifact id of the bundle
	 * @param version - the version of the bundle
	 * @return the String representing the URL location of this bundle
	 */
	public Resource locateArtifact(String groupId, String artifactId, String version) {
		return locateArtifact(groupId, artifactId, version, DEFAULT_ARTIFACT_TYPE);
	}

	/**
	 * Find a local ivy artifact. First tries to find the resource as a
	 * packaged artifact produced by a local ivy build, and if that fails will
	 * search the local ivy repository.
	 * NOTE: This method forms part of the ArtifactLocator interface
	 * 
	 * @param groupId - the groupId of the organization supplying the bundle
	 * @param artifactId - the artifact id of the bundle
	 * @param version - the version of the bundle
	 * @param type - the extension type of the artifact
	 * @return
	 */
	public Resource locateArtifact(final String groupId, final String artifactId, final String version,
			final String type) {
		init();
		Resource localIvyBundle = localIvyBundle(groupId, artifactId, version, type);
		return localIvyBundle;
	}

	/**
	 * Return the resource of the indicated bundle in the local Ivy repository
	 * 
	 * @param groupId - the groupId of the organization supplying the bundle
	 * @param artifact - the artifact id of the bundle
	 * @param version - the version of the bundle
	 * @return
	 */
	protected Resource localIvyBundle(String groupId, String artifact, String version, String type) {
	    StringBuffer location = new StringBuffer(groupId);
		location.append(SLASH_CHAR);
		location.append(artifact);
		location.append(SLASH_CHAR);
		location.append("jars");
		location.append(SLASH_CHAR);
		location.append(artifact);
		location.append('-');
		location.append(version);
		location.append(".");
		location.append(type);

		return new FileSystemResource(new File(repositoryHome, location.toString()));
	}
}
