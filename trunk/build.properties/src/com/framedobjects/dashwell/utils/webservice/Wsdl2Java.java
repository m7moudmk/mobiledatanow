package com.framedobjects.dashwell.utils.webservice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Service;
import org.apache.axis.wsdl.toJava.Emitter;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;

import wsl.fw.exception.MdnException;
import wsl.fw.util.Config;
import wsl.mdn.server.LicenseManager;



public class Wsdl2Java {

	//http://developer.ebay.com/webservices/latest/eBaySvc.wsdl 
	/**- timeout*/
	
	//https://www.google.com/api/adsense/v2/SiteFilterService?wsdl 
	/**--Could not read/write to/from file.
	//---Details --	java.lang.NullPointerException --	null
	 * */
	
	//http://code.google.com/apis/coupons/basic_coupon_sample.xml //not wsdl
	//http://api.google.com/GoogleSearch.wsdl
	
	//http://mail.yahooapis.com/ws/mail/v1.1/wsdl
	/**
	 * An error occured while parsing the wsdl document.

Details --
The definition of {urn:yahoo:ymws}MetaData results in a loop.
	 */
	
	
	//https://adcenterapi.microsoft.com/v4/CampaignManagement/CampaignManagement.asmx?wsdl
	/**
	 * Could not read/write to/from file.

Details --
java.lang.NullPointerException
null
	 */
	
	//http://soap.search.msn.com/webservices.asmx?wsdl 
	/**--working in the first step
	//AppId: C404D67EAC4A0841A6CBAA97CA8394257F97AF80 (Firetrust.Ltd)
	 * 
	 */
	
	//http://terraserver-usa.com/TerraService2.asmx?WSDL 
	/**
	 *  -- working in the first step
	 *  -- the request send int 
	 *  -- response is form also ???
	 */
	
	
	//http://webservices.amazon.com/AWSECommerceService/AWSECommerceService.wsdl?
	/**
	 * Java Compile Error.
This usually means, that your WSDL Documents contains errors.

Details --
Compile failed; see the compiler error output for details.

	 */
	
	//http://s3.amazonaws.com/ec2-downloads/ec2.wsdl?
	/**
	 * Java Compile Error.
This usually means, that your WSDL Documents contains errors.

Details --
Compile failed; see the compiler error output for details.

	 */
	
	//http://mechanicalturk.amazonaws.com/AWSMechanicalTurk/AWSMechanicalTurkRequester.wsdl?
	/**
	 * working in the first step
	 * -- do not understand the parameter value
	 */
	
	//http://queue.amazonaws.com/doc/2006-04-01/QueueService.wsdl?
	/**
	 * Java Compile Error.
This usually means, that your WSDL Documents contains errors.

Details --
Compile failed; see the compiler error output for details.

	 */
	
	//http://websearch.amazonaws.com/doc/2006-02-15/AlexaWebSearch.wsdl?
	/**
	 * Java Compile Error.
This usually means, that your WSDL Documents contains errors.

Details --
Compile failed; see the compiler error output for details.

	 */
	
	//http://ats.amazonaws.com/doc/2005-11-21/AlexaTopSites.wsdl?
	/**
	 *  Java Compile Error.
This usually means, that your WSDL Documents contains errors.

Details --
Compile failed; see the compiler error output for details.

	 */
	
	//http://ast.amazonaws.com/doc/2006-05-15/AlexaSiteThumbnail.wsdl? 
	/**--working in the first step
	 * 
	 */
	
	//https://www.google.com/api/adsense/v2/SiteFilterService?wsdl
	/**
	 * Could not read/write to/from file.

Details --
java.lang.NullPointerException
null
	 */
	
	//http://code.google.com/apis/coupons/basic_coupon_sample.xml //not wsdl
	
	//http://www.bloglines.com/search?format=publicapi&apiuser=myusername&apikey=275938797F98797FA9879AF&q=bloglines+freedback
		
	//http://newsisfree.com/nifapi.wsdl
	/**
	 * An error occured while parsing the wsdl document.

Details --
Type boolean is referenced but not defined.

	 */

	//http://geocoder.us/dist/eg/clients/GeoCoder.wsdl
	/**
	 * first step working
	 * type any string, it clear all the input and start again
	 */
	
	//http://www.ejse.com/WeatherService/Service.asmx?WSDL
	/**
	 * first step working
	 * give int get result
	 * An error occurred invoking the specified method.

Server was unable to process request. --> Object reference not set to an instance of an object.
	 */
	
	//http://ws.strikeiron.com/ypcom/yp1?WSDL
	/**
	 * first step working
	 * need to find parameter value (An error occurred invoking the specified method.

No user identifier provided
	 * )
	 */
	
	//http://ws.strikeiron.com/ReversePhoneLookup?WSDL
	/**
	 * first step working
	 * need to find parameter value
	 * An error occurred invoking the specified method.

No user identifier provided
	 */
	
	//http://ws.strikeiron.com/donotcall2_5?WSDL
	/**
	 * first step working
	 * need to find parameter value
	 * An error occurred invoking the specified method.

No user identifier provided
	 */
	
	//http://ws.strikeiron.com/ResidentialLookup3?WSDL
	/**
	 * first step working
	 * need to find parameter value
	 * An error occurred invoking the specified method.

No user identifier provided
	 */
	
	
	private String outputDirName = null;//"C:/Test/Output/";
	private String m_wsdlUrl = null;//"http://www.ejse.com/WeatherService/Service.asmx?WSDL";
	protected JMDNFile m_jMdnFile = null;	
    private boolean enableCaching = true;
    private String connectTimeout = "5";
    private String readTimeout = "60";
    private URLClassLoader m_jarLoader;
    protected ClassHelper m_classHelper;
    
    protected List<Class> m_wsdlServiceInterface;
    protected List<Class> m_wsdlSDI;
    protected List<Class> m_wsdlTypes;
    protected List<Class> m_wsdlHolders;
    protected List<Class> m_wsdlStub;
    protected List<Class> m_wsdlServiceLocator;	
    
    protected List<ServiceInfo> m_services;
    
    protected ServiceInfo m_currService;
    protected PortInfo m_currPort;
    protected OperationInfo m_currOperation;
    protected String m_currWsdlUrl;
    
	public Wsdl2Java(String mWsdlUrl) throws MdnException {
		
		outputDirName = LicenseManager.getWebServiceCompileFilePath();
		
		m_wsdlUrl = mWsdlUrl;
	  	    
	    m_wsdlTypes = new ArrayList<Class>();
	    m_wsdlHolders = new ArrayList<Class>();
	    m_wsdlSDI = new ArrayList<Class>();
	    m_wsdlStub = new ArrayList<Class>();
	    m_wsdlServiceInterface = new ArrayList<Class>();
	    m_wsdlServiceLocator = new ArrayList<Class>();
	    
	    m_services = new ArrayList<ServiceInfo>();
	    
	  	m_jMdnFile = new JMDNFile(m_wsdlUrl, outputDirName);  
	    // classHelper
	    m_classHelper = new ClassHelper(m_jarLoader);	   
	    
	    initWebService();
	}
	
	protected void initWebService() throws MdnException{
	    getWsdl();
	    buildComponents();
	    
		/* set current service and port */
	    m_currService = m_services.get(0);
	    m_currPort = m_currService.getPort(0);
	    m_currOperation = m_currPort.getOperation(0);
	    
	    // classHelper 
	    // TODO fix that jarloader problem, we don't want to create classhelper twice
	    m_classHelper = new ClassHelper(m_jarLoader);	
	    m_currWsdlUrl = m_wsdlUrl;
	}
 
    
	  public void getWsdl() throws MdnException{
		    
		  boolean wsdlChanged = true;
		    long newChecksum = 0;
		    long oldChecksum = 0;
		    String wsdlFile = null;
		    
		    File jarfile = new File(outputDirName 
		        + m_jMdnFile.getWsdlLocalName() + ".jar");
		    
		    System.out.println("Getting wsdl: " + m_wsdlUrl);
		    
		    /* caching: checksum of WSDL file */
		    if (Boolean.valueOf(enableCaching)){
		      
		      try {
		        URL wsdlUrl = new URL(m_wsdlUrl);
		        
		        URLConnection conn = null;
		        
		        /* dispatch https/http */
		        if (wsdlUrl.getProtocol().equals("http")){
		          conn = (HttpURLConnection) wsdlUrl.openConnection();
		        }
		        else if (wsdlUrl.getProtocol().equals("https")){
		          /*
		          TrustManager[] trustManager = 
		            new TrustManager [] { m_trustManager };
		          
		          SSLContext sContext = null;
		          try {
		            sContext = SSLContext.getInstance("SSL");
		            sContext.init(null, trustManager, new java.security.SecureRandom());
		          }
		          catch (KeyManagementException e) {
		            throw new MdnException(
		                Properties.getMessage("error.KeyManagementException"), e);
		          }
		          catch (NoSuchAlgorithmException e) {
		            throw new MdnException(
		                Properties.getMessage("error.NoSuchAlgorithmException"), e);
		          }
		          
		          HttpsURLConnection.setDefaultSSLSocketFactory(sContext.getSocketFactory());
		          conn = (HttpsURLConnection) wsdlUrl.openConnection();
		          */
		          /* set the truststore
		           * take temporary truststore if not accepted permanently 
		           */
//		          String trustStore = Properties.getConfig("network.keyStore");
//		          if (m_trustManager.isTemporaryValid())
//		            trustStore = System.getProperty("java.io.tmpdir") 
//		            + File.separator + trustStore;
		        	
//		          System.setProperty("javax.net.ssl.trustStore", 
//		              m_trustManager.getkeystorePath());
		        	
		        /*help link 
		         	* http://forums.sun.com/thread.jspa?forumID=2&threadID=295242
		         */		        	
		        }
		        
		        // timeout values in seconds
		        int connect_timeout = Integer.parseInt(connectTimeout);
		        int read_timeout = Integer.parseInt(readTimeout);
		        // timeout values needed in milliseconds
		        conn.setConnectTimeout(connect_timeout * 1000);
		        conn.setReadTimeout(read_timeout * 1000);

		        InputStream in = conn.getInputStream();
		        
		        StringBuffer content = new StringBuffer();
		        byte[] buf=new byte[0xFFFF];
		        int len;
		        while ((len = in.read(buf)) > 0) {
		          // somehow axis makes too many line breaks. we don't want them
		          String str = new String(buf, 0, len);
		          content.append(str.replaceAll("[\n\r]{2,}", "\n"));
		        }
		        in.close();
		        
		        /* check if it is actually a WSDL document */
		        if (!content.toString().toLowerCase().trim().endsWith("definitions>"))
		          throw new MdnException(Config.getProp("error.notAWSDLDocument"));
		        
		        // TODO: download WSDL first, such that afterwards it doesn't
		        // have to be read remotely again
		        // TODO: download referenced files too (e.g. external xsd files)
		        // <xsd:import schemaLocation="XWebEmailValidation.xsd"
		        
		        // set wsdlUrl to local file (avoid refetching of remote one)
		        wsdlFile = outputDirName 
		            + m_jMdnFile.getWsdlLocalName() + ".wsdl";
		        
		        // write WSDL to local file
		        BufferedWriter out = new BufferedWriter(
		            new FileWriter(wsdlFile));
		        out.write(content.toString());
		        out.close();
		        
		        newChecksum = m_jMdnFile.generateChecksum(content.toString());
		        oldChecksum = m_jMdnFile.getChecksum();
		        System.out.println("Wsdl Checksum (" + m_jMdnFile.getWsdlLocalName() + "): old " 
		            + oldChecksum + ", new " + newChecksum);
		        
		        if (newChecksum == oldChecksum && jarfile.exists()){ 
		          wsdlChanged = false;
		          System.out.println("Checksum didn't change, no need to recompile");
		        }
		        //else addChecksum(newChecksum);
		      }
		      catch(SocketTimeoutException e){
		    	  e.printStackTrace();
		    	  throw new MdnException(Config.getProp("error.SocketTimeoutException"), e);
		      }
		      catch(MalformedURLException e){
		    	  e.printStackTrace();
		    	  throw new MdnException(Config.getProp("error.MalformedURLException"), e);
		      }
		      catch (IOException e) {
		    	  e.printStackTrace();
		    	  throw new MdnException(Config.getProp("error.IOException"), e);
		      }
		    }
		    else wsdlChanged = true;
		    
		    // classHelper 
		    // TODO fix that jarloader problem, we don't want to create classhelper twice
//		    try {
		    	System.out.println(m_jarLoader);
				//TODO: m_classHelper = new ClassHelper(m_jarLoader);
//			} catch (MdnException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
			
		    /* now compile the web service */
		    compileWebService(wsdlChanged, jarfile, newChecksum);
		  }	
	private void compileWebService(boolean wsdlChanged, File jarfile, long newChecksum) throws MdnException {
		Emitter parser = null;
		/* Parse the WSDL (invoke wsdl2java) */
        try {
			parser = new Emitter();
		} catch (RuntimeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        parser.setOutputDir(outputDirName);
        parser.setBuildFileWanted(true);
        parser.setAllWanted(true);
        //parser.setImports(true);
        //parser.setNowrap(true); // --> if set, methods may contain too many args
        //parser.setHelperWanted(true);
        //parser.setServerSide(true);
        //parser.setSkeletonWanted(true);
        parser.setWrapArrays(false);
        
        try{ 
//        TODO: download included files for WSDL too (see above)
          parser.run(/*wsdlFile*/m_wsdlUrl);
        }
        catch(Exception e){
          //throw new Exception(Config.getProp("error.wsdl2javaParse"), e);
        	e.printStackTrace();
        	throw new MdnException("error.wsdl2javaParse", e);
        }        
        /* Compile the classes and create jar (invoke ant) */
        Project project = new Project();
        ProjectHelper helper = ProjectHelper.getProjectHelper();
    
        project.init();
        helper.parse(project, new File(outputDirName + "build.xml"));
        
        /* add source files to jar file */
        // create copy task
        Copy copyTask = (Copy)project.createTask("copy");//new Copy();
        FileSet fileset = new FileSet();
        String srcProperty = project.getProperty("src");
        fileset.setDir(new File(srcProperty));
        fileset.setCaseSensitive(true);
        fileset.setIncludes("**/*.java");
        String buildProperty = project.getProperty("build.classes");
        copyTask.setTodir(new File(buildProperty));
        copyTask.addFileset(fileset);
        
        Target jarTarget = (Target)project.getTargets().get("jar");
        
        // create include source target (properties as jar target)
        Target newJarTarget = new Target();
        newJarTarget.setName("includeSrc");
        newJarTarget.setLocation(jarTarget.getLocation());
        newJarTarget.setProject(jarTarget.getProject());
        
        // add copy task to target
        newJarTarget.addTask(copyTask);
        // jar target depends on include src target 
        jarTarget.setDepends("includeSrc");
        // add target to project
        project.addOrReplaceTarget("includeSrc", newJarTarget); 
        
        /* disable ant warnings */
        Target compileTarget = (Target)project.getTargets().get("compile");
        Task[] compileTasks = compileTarget.getTasks();
        RuntimeConfigurable compileConfig;
        for(int i = 0; i < compileTasks.length; ++i){
          if(compileTasks[i].getTaskName().equals("javac")){
            compileConfig = compileTasks[i].getRuntimeConfigurableWrapper();
            compileConfig.setAttribute("nowarn","true");
          }   
        }
        

        
        System.out.println("Starting Compilation...");
        try {
          project.executeTarget("jar");
        }
        catch (BuildException e) {
          //throw new MdnException(Config.getProp("error.BuildException"), e);
        	e.printStackTrace();
        }
        finally{
          // remove generated sources
        	m_jMdnFile.cleanOutputDir();
        }
        
        //System.out.println("Generated jar file: " + jarfile.getAbsolutePath()); 		

        try{ 
            m_jarLoader = new URLClassLoader(new URL[] { jarfile.toURL() }); 
          }
          catch(MalformedURLException e){
            e.printStackTrace();
        	  //throw new MdnException(Config.getProp("error.MalformedURLException"), e);
          }
          
          /* now load the classes */
          try {
			loadClasses(wsdlChanged, parser, newChecksum);
		} catch (MdnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	
  private void loadClasses(boolean wsdlChanged, Emitter parser, long newChecksum) 
      throws MdnException{
    /* Classify classes */
    List generatedClasses = null;
    if (wsdlChanged){
      generatedClasses = parser.getGeneratedClassNames();
      m_jMdnFile.createJMDNFile(generatedClasses, newChecksum);
    }
    else
      generatedClasses = m_jMdnFile.getGeneratedClassNames();
    
    for(Object o : generatedClasses){
      if (o != null){
        String classname = o.toString();
        System.out.println("classname: " + classname);
        Class cl = null;
        try{ 
          cl = m_jarLoader.loadClass(classname);
        }
        catch(ClassNotFoundException e){
          throw new MdnException(Config.getProp("error.ClassNotFoundException"), e);
        }
        
        /* categorize the class */
        if (cl.isInterface()){
          // service interface
          if (m_classHelper.hasInterface(cl, "javax.xml.rpc.Service")){
            m_wsdlServiceInterface.add(cl);
            System.out.println("Got class: service interface: " + cl.getCanonicalName());
          }
          // sdi (service definition interface, aka porttypes)
          else if(m_classHelper.hasInterface(cl, "java.rmi.Remote")){
            m_wsdlSDI.add(cl);
            System.out.println("Got class: SDI: " + cl.getCanonicalName());
          }
        }
        else if (!cl.isArray() && !cl.isPrimitive()){ // is class
          // service locator 
          if (m_classHelper.hasSuperclass(cl, "org.apache.axis.client.Service")){
            m_wsdlServiceLocator.add(cl);
            System.out.println("Got class: service locator: " + cl.getCanonicalName());
          }
          // stub (aka binding)
          else if (m_classHelper.hasSuperclass(cl, "org.apache.axis.client.Stub")){
            m_wsdlStub.add(cl);
            System.out.println("Got class: stub: " + cl.getCanonicalName());
          }
          // holder
          else if(m_classHelper.hasInterface(cl, "javax.xml.rpc.holders.Holder")){
            m_wsdlHolders.add(cl);
            System.out.println("Got class: holder: " + cl.getCanonicalName());
          }
          // type (bean in most cases)
          else if (m_classHelper.hasInterface(cl, "java.io.Serializable")){
            m_wsdlTypes.add(cl);
            System.out.println("Got class: type: " + cl.getCanonicalName());
          }
          // unknown class type
          else{
        	  System.out.println(Config.getProp("error.unknownClassType"));
                //new String[]{cl.getCanonicalName()}));
          }
        }
      }
    }
  }
  /**
   * Build all the components for the Web Service,
   * i.e. extract all services, ports and operations.
   * 
   * @throws MdnException
   */
  private void buildComponents() throws MdnException{
    try{ 
      
      // each class
      for (Class cl : m_wsdlServiceLocator){
        // Service Locator
        Service serviceLoc = (Service)cl.newInstance();
        serviceLoc.setMaintainSession(true);

        QName servicename = serviceLoc.getServiceName();
        
        ServiceInfo serviceInfo = new ServiceInfo(servicename.getLocalPart());
        serviceInfo.serviceLocator = serviceLoc;
        
        Iterator<QName> it = serviceLoc.getPorts();

        // all ports
        while(it.hasNext()){
          QName name = it.next();
          
          Remote port = (Remote)serviceLoc.getPort(name, null);

          Class portClass = port.getClass();
          
          PortInfo portInfo = new PortInfo(name.getLocalPart());
          portInfo.portClass = portClass;
          portInfo.portInstance = port;
          portInfo.qName = name;
  
          Method[] methods = portClass.getDeclaredMethods();
          // all operations
          for (Method m : methods){
            if ((m.getModifiers() & Modifier.PUBLIC) != 0){;
              portInfo.addOperation(m.getName(), m.getParameterTypes(),m.getReturnType());
            }
          }
          serviceInfo.addPort(portInfo);
        }
        
        m_services.add(serviceInfo);
        
        /* assure everything is fine */
        if (m_services == null || m_services.size() == 0)
          throw new MdnException("error.soapLogicNoServices");
        if (m_services.get(0).numPorts() == 0)
          throw new MdnException("error.soapLogicNoPorts");
        if (m_services.get(0).getPort(0).numOperations() == 0)
          throw new MdnException("error.soapLogicNoOperations");
        
        System.out.println("Web Service:\n" + m_services.get(m_services.size() -1));
      }
      
    }
    catch(InstantiationException e){
      throw new MdnException("error.InstantiationException", e);
    }
    catch(IllegalAccessException e){
      throw new MdnException("error.IllegalAccessException", e);
    }
    catch(ServiceException e){
      throw new MdnException("error.ServiceException", e);
    }
  }
  public void setCurrentOperation(String operation) throws MdnException{
    OperationInfo oi = null;
    for (OperationInfo o : m_currPort.operations){
      if (o.name.equalsIgnoreCase(operation)){
        oi = o;
        break;
      }
    }
    if (oi == null) 
      throw new MdnException("error.noSuchOperation");
    m_currOperation = oi;
  }

  public void setCurrentPort(String port) throws MdnException{
    PortInfo pi = null;
    for (PortInfo p : m_currService.ports){
      if (p.name.equalsIgnoreCase(port)){
        pi = p;
        break;
      }
    }
    if (pi == null) 
      throw new MdnException("error.noSuchPort");
    m_currPort = pi;
  }

  public void setCurrentService(String service) throws MdnException{
    ServiceInfo si = null;
    for (ServiceInfo s : m_services){
      if (s.name.equalsIgnoreCase(service)){
        si = s;
        break;
      }
    }
    if (si == null)
      throw new MdnException("error.noSuchService");
    m_currService = si;
  }  	
}