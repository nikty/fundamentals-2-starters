/*
  Exercise 10.6 Take a look at this following class:

  // represent information about an image
  // class Image {
  //   int width; // in pixels
  //   int height; // in pixels
  //   String source;
  //   Image(int width, int height, String source) {
  //     this.width = width;
  //     this.height = height;
  //     this.source = source;
  //   }
  // }

  Design the method sizeString for this class. It produces one of three
  strings, depending on the number of pixels in the image:

  1. "small" for images with 10,000 pixels or fewer;

  2. "medium" for images with between 10,001 and 1,000,000 pixels;

  3. "large" for images that are even larger than that.

  Remember that the number of pixels in an image is determined by the
  area of the image.
*/

import tester.*;

// represent information about an image
class Image {
    int width; // in pixels
    int height; // in pixels
    String source;
    
    Image(int width, int height, String source) {
        this.width = width;
        this.height = height;
        this.source = source;
    }

    // Produce text representation of the size of this image
    String sizeString() {
        // ...
    }
}

class ImageExamples {
    // ...
}
