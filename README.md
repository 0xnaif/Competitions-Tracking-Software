# To run the program successfully, you should do the following before running:
1- make sure that you have added JavaFx and Apache POI libraries.(from this link you can add Apache library: https://www.youtube.com/watch?v=w757wjTiruU&ab_channel=LearnAutomationStepByStep).

2- you have to write three lines below on VM arguments:
	--module-path {your path of javafx-sdk} --add-modules javafx.controls
	--add-modules=javafx.web
	-Dcom.sun.webkit.useHTTP2Loader=false

3- you have a file has this name: "Email Body template.txt" and excel file has this name: "malaf.xlsx" on the same directory path of the project.
