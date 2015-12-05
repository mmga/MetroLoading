

A Metro-style loading progress with some configurable attributes

##Preview
![](https://github.com/mmga/MetroLoading/blob/master/gifs/1.original.gif)



##Usage
###1.Setup Gradle

```groove
<dependencies {
       compile 'com.mmga.metroloading:metroloading:1.0.0'
    } 
```


###2.Add MetroLoadingView to your layout
```xml
<com.mmga.metroloading.MetroLoadingView
        android:id="@+id/loading"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" 
		
		app:transform_color="#ee00ff"
        app:indicator_height="9dp"
        app:indicator_width="4dp"
		/>
```

###3.use it as a normal custom view
```java
  mLoading.start();
  ```

now you should have got the loading animation as Preview

##Customization

###1.change the speed,interval and number of indicator
```xml
	app:number="3"
	app:duration_in_mills="1500"
	app:interval_in_mills="100"
```
![](https://github.com/mmga/MetroLoading/blob/master/gifs/5_speed.gif)

###2.fade in an out
```xml
	app:fade="true"
```
![](https://github.com/mmga/MetroLoading/blob/master/gifs/2_fade.gif)


###3.change the scale
```xml
	app:transform="true"
	app:transform_height="4dp"
	app:transform_width="4dp"
	app:transform_radius="3dp"
```
![](https://github.com/mmga/MetroLoading/blob/master/gifs/3_scale.gif)<br>
this dimension is the size when indicators move to just middle of the view

###4.add a shadow
```xml
	app:has_shadow = "false"
	app:shadow_color="#666666"
```
![](https://github.com/mmga/MetroLoading/blob/master/gifs/4_shadow.gif)

###5.make it colorful
```xml
	app:transform_color_mode="symmetry"
	app:transform_color="#ee00ff"
```
![](https://github.com/mmga/MetroLoading/blob/master/gifs/6_linearColor.gif)<br>
transform_color_mode = lineary<br>

![](https://github.com/mmga/MetroLoading/blob/master/gifs/7_symmetryColor.gif)<br>
transform_color_mode = symmetry<br>


###6."Coach! I want to play ball"
Take it
```xml
	app:indicator="circle"
```
attributes above are all available for the ball<br>
![](https://github.com/mmga/MetroLoading/blob/master/gifs/8_circle.gif)

---

###Options available in XML attributes:
|Attribute         			 |Description     											|
|---               			 |---      													|
|indicator         			 |shape of indicator,"rectangle" or "circle"         		|
|indicator_color    		 |color    		|		  
|indicator_width    		 |dimension  	|	       
|indicator_height			 |dimension     |			
|indicator_radius    		 |dimension     |		    
|duration_in_mills			 |integer       |     
|interval_in_mills           |integer     	|     
|number						 |integer	 	| 		
|          					 |  			|  
|transform    				 |boolean     	|     
|transform_height        	 |dimension,the height when comes to the middle     |     
|transform_width       		 |dimension     |     
|transform_radius       	 |dimension     |     
|   						 | 				|  
|transform_color_mode  		 |"none","linear","symmetry"   |     
|transform_color			 |the color in the end(Linear-mode) or middle(symmetry)		|	  
|fade        				 |boolean     	|     
|   						 |  			|  
|has_shadow       			 |boolean     	|     
|shadow_color       		 |color     	|  


###API available
|Method	                 | 
|---                     | 
|start()	    		 |
|stop()	        		 |
|isAnimating()			 |



##About me
I am just a very beginner in coding and this project is just a practice of knowledge I learn recently.<br>
So,there must be something confusing or even mistake. Please let me know. Thanks <br>
[Email](1034752946@qq.com)




## License

Copyright (c) 2015 mmga

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

