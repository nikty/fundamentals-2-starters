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
        if (this.size() <= 10000) {
            return "small";
        } else if (10000 < this.size() && this.size() <= 1000000) {
            return "medium";
        } else {
            return "large";
        }
    }

    // calculate image size in pixels
    int size() {
        return this.height * this.width;
    }

}

class ImageExamples {
    Image i1 = new Image(10, 20, "dummy");
    Image i2 = new Image(100, 100, "dummy");
    Image i3 = new Image(1000, 1000, "dummy");
    Image i4 = new Image(1000, 10000, "dummy");

    boolean testSizeString(Tester t) {
        return t.checkExpect(i1.sizeString(), "small")
            && t.checkExpect(i3.sizeString(), "medium")
            && t.checkExpect(i4.sizeString(), "large");
    }
}
