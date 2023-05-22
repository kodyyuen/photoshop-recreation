#Photoshop Recreation

This project was an attempt at a recreation of photoshop. The program included some of photoshop's features such as applying filters (blurring and sharpening) and transformations (greyscale and sepia) to images, downscaling images, and saving and loading images. Similar to photoshop, users were also able to manipulate layers of images. They were able to make any layer visible/invisible and apply any of the previous features to it. If the second layer was greyscaled and the first layer was made invisible, the GUI would display the greyscaled layer. The program also supports the option to save and load images in ppm, jpg, and png. It is also able to hold the state of the image, so if the image described before was saved, it would be able to load the image back with the first layer invisible and the second layer greyscaled and the user would be able to pick up where they left off.

In the terminal, cd into res and type "java -jar Homework7.jar " followed by
-script "filepath"
-text
-interactive

where script requires the filepath of the text file if you want to script
text will open up the console for you to type your commands. How to use that is found right below this.
interactive will open up the GUI. How to use that is found at the bottom.

Example:
console
script script.txt

CREATE layer
Creates a layer with a specified name. The layer is visible by default.

Example:
create first

CURRENT layer
Sets the given layer name to the current layer.

Example:
current first

COPY layername newlayername
Copies the given layer's name to a new layer. The first argument is the layer's name to be copied.
The second argument is the new layer's name that will be a copy.

Example:
create first
copy first second

REMOVE layername
Removes the layer that matches the inputted name.

Example:
create first
remove first

LOAD filepath
Loads the given filepath into the current layer. A current layer needs to be selected
or the console will prompt you with an error. An error will also be thrown if the file
path is invalid. You can replace the loaded image in the current layer by calling load 
again. The filepath is also just the file name (no need for res/filepath).

Example:
load image.jpg

CHECKERBOARD tilesize numtiles
This is another way to load an image into a layer. This loads a programmatically created
image into the current layer. It requires the size of the tile and the number of tiles
on one side as numbers. A layer must exist and be set as current for the image to load into it.

Example:
checkerboard 50 5       will create a checkerboard image with tiles 50 pixels wide and
                        the board will be 5 tiles wide
						
SAVE filepath filetype
Saves the topmost visible layer to the filepath given. The current layer also must have
an image loaded into it.

Example:
save example jpg
save example ppm
save example png

BLUR, SHARPEN, GREYSCALE, SEPIA
All of these commands are typed on a new line. It applies the feature to the current
layer. If no layer is current, it will throw an error. The layer must also have an image
loaded into it.

Example:
create first
current first
blur #blurs the layer "first"

VISIBLE/INVISIBLE layer
These commands are called to make the given layer either invisible or visible. The layer
name must exist for this command to work.

Example:
create first
create second
current second
invisible first

MULTISAVE filepath filetype
Calling multisave with a filetype (png, jpg, ppm) will save all current layers' images
individually with the specified filetype (they will be called layer-(number of layer).filetype)
as well as a text file containing the script to load them back in (called multi-layer.txt). They will
be saved to the filepath. All layers need to have an image loaded in for this command to work. 
Visibility does not matter.

Example:
create first
create second
current second
load checkerboard 2 2
current first
load koala.ppm ppm
multisave jpg

MULTILOAD filepath
Calling multiload with the filepath will load all of the layers and their previous
visibility statuses all at once. The expected filepath is to a text file.

Example:
multiload multi-layer.txt



GUI OPERATIONS ------------------------------------------------------------------------------------------------

The menu at the top displays all of the available operations.

Arrange Layers:
CREATE: you must have a name at the argument text box at the bottom. When you want to create a layer, you type
into the argument box first, then click create.

SET CURRENT: the same procedure as CREATE. Sets the given layer to current.

REMOVE: the same procedure as CREATE. Removes the given layer.

COPY: takes two arguments: layer name to be copied and the new copy layer's name. If you wanted to copy the layer
first into the new layer firstcopy, you would type "first firstcopy" into the argument box and click copy in the menu.

VISIBLE: the same procedure as CREATE. Sets the given layer to visible.

INVISIBLE: the same procedure as CREATE. Sets the given layer to invisible.


Layer Manipulation:
BLUR: blurs the current layer.

SHARPEN: sharpens the current layer.

GREYSCALE: greyscales the current layer.

SEPIA: sepias the current layer.

DOWNSCALE: Downscales all of the layers. The two arguments required are the x-scale and the y-scale.
If you wanted to downscale the image by 0.4 and 0.3, you would type into the argumen field "0.4 0.3" and
click downscale. Only values between 0 and 1 exclusive are allowed.


Save/Load:
SAVE: opens a prompt that will let you select a directory. Then, in the prompt, enter the filename that you want.
The file will be saved at that location. A pop-up window will show asking for a file type. That will be the file type
of the saved image. If you enter something that is not PPM/JPG/PNG or nothing, it will default to JPG.

SAVE: opens a prompt that will let you select a directory. Then, in the prompt, enter the folder name that you want.
The text file and images will be saved at that location. A pop-up window will show asking for a file type.
That will be the file type of the saved image(s). If you enter something that is not PPM/JPG/PNG or nothing, it will
default to JPG.

LOAD: opens a prompt that will let you select an image. It loads the image to the current layer.

MULTI-LOAD: opens a prompt that will let you select a text file. It loads the script to the GUI.

CHECKERBOARD: loads a checkerboard image into the current layer. The arguments it takes are tile size and number of tiles.
So, if I wanted to load a checkerboard into the current layer with tile size 20 and 8 tiles per side, I would
type "20 8" into the argument box and click checkerboard. Make sure that a layer is set to current.

The script file for the GUI is located in the res/ folder called scriptfolder. What it does is that it loads
two already existing images into the GUI and saves those again in the res/ folder as newscriptfolder.

**Make sure that the JAR is run in the /res folder**

