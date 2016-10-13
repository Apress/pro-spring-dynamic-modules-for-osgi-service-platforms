<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
      <div id="index.body">
        <div id="main">
                <h2>Hello World Messages </h2>
                <tiles:insertAttribute name="helloMessages"/>
        </div>
        <div id="sidebar">
            <h2>Translators </h2>
               <tiles:insertAttribute name="helloTranslators"/>

               <tiles:insertAttribute name="translator"/>
        </div>
    </div>
