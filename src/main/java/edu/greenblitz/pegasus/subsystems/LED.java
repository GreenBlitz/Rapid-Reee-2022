package edu.greenblitz.pegasus.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;


public class LED extends GBSubsystem{

    private int port;
    private LED instance;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer ledBuffer;
    private LED (int pwmPort){
        this.port = pwmPort;
        this.addressableLED = new AddressableLED(port);
        this.ledBuffer = new AddressableLEDBuffer(60);
    }
    public void setColor (Color color){
        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setLED(i,color);
        }
    }


}
