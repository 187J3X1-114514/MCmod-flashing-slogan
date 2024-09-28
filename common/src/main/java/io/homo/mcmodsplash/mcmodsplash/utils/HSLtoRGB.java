package io.homo.mcmodsplash.mcmodsplash.utils;

public class HSLtoRGB {
    public static RGBColor hsl2Rgb(double hue, double saturation, double luminosity)
    {
        final float h = (float) hue;
        final float s = (float) saturation;
        final float l = (float) luminosity;

        final float c = (1f - Math.abs(2 * l - 1f)) * s;
        final float m = l - 0.5f * c;
        final float x = c * (1f - Math.abs((h / 60f % 2f) - 1f));

        final int hueSegment = (int) h / 60;

        int r = 0;
        int g = 0;
        int b = 0;

        switch (hueSegment)
        {
            case 0:
                r = Math.round(255 * (c + m));
                g = Math.round(255 * (x + m));
                b = Math.round(255 * m);
                break;
            case 1:
                r = Math.round(255 * (x + m));
                g = Math.round(255 * (c + m));
                b = Math.round(255 * m);
                break;
            case 2:
                r = Math.round(255 * m);
                g = Math.round(255 * (c + m));
                b = Math.round(255 * (x + m));
                break;
            case 3:
                r = Math.round(255 * m);
                g = Math.round(255 * (x + m));
                b = Math.round(255 * (c + m));
                break;
            case 4:
                r = Math.round(255 * (x + m));
                g = Math.round(255 * m);
                b = Math.round(255 * (c + m));
                break;
            case 5:
            case 6:
                r = Math.round(255 * (c + m));
                g = Math.round(255 * m);
                b = Math.round(255 * (x + m));
                break;

            default:
                break;
        }

        return new RGBColor(r, g, b);
    }

    public static class RGBColor{
        public int red;
        public int green;
        public int blue;

        public RGBColor(int red, int green,int blue){
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public int toInt(){
           return (int)Long.parseLong(String.format("%02X%02X%02X", this.red,this.green,this.blue),16);
        }
    }
}