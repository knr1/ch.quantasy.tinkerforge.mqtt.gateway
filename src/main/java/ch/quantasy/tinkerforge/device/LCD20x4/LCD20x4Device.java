/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.tinkerforge.device.LCD20x4;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LCD20x4Device extends GenericDevice<BrickletLCD20x4, LCD20x4DeviceCallback> {

    private Boolean isBacklightEnabled;
    private DeviceConfigParameters configParameters;
    private final Set<DeviceCustomCharacter> customCharacters;
    private final Set<DeviceDefaultText> defaultTexts;
    private Integer defaultTextCounter;
    private final StringBuffer[] lines;
    private Thread writerThread;
    private Writer writer;

    public LCD20x4Device(TinkerforgeStackAddress address, BrickletLCD20x4 device) throws NotConnectedException, TimeoutException {
        super(address, device);
        writer = new Writer();

        this.customCharacters = new HashSet<>();
        this.defaultTexts = new HashSet<>();
        lines = new StringBuffer[4];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new StringBuffer("                    ");
        }
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addButtonPressedListener(super.getCallback());
        getDevice().addButtonReleasedListener(super.getCallback());
        writerThread = new Thread(writer);
        writerThread.start();
        if (this.isBacklightEnabled != null) {
            setBacklight(isBacklightEnabled);
        }
        if (this.configParameters != null) {
            setConfigParameters(configParameters);
        }
        if (!this.customCharacters.isEmpty()) {
            setCustomCharacters(customCharacters.toArray(new DeviceCustomCharacter[0]));
        }
        if (!this.defaultTexts.isEmpty()) {
            setDefaultText(defaultTexts.toArray(new DeviceDefaultText[0]));
        }
        if (this.defaultTextCounter != null) {
            setDefaultTextCounter(defaultTextCounter);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        writerThread.interrupt();
        writerThread = null;
        getDevice().removeButtonPressedListener(super.getCallback());
        getDevice().removeButtonReleasedListener(super.getCallback());
    }

    public void setBacklight(Boolean isBacklightEnabled) {
        try {
            if (isBacklightEnabled) {
                getDevice().backlightOn();;
            } else {
                getDevice().backlightOff();
            }
            this.isBacklightEnabled = getDevice().isBacklightOn();
            super.getCallback().backlightChanged(this.isBacklightEnabled);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearDisplay(Boolean clearDisplay) {
        try {
            if (!clearDisplay) {
            }
            getDevice().clearDisplay();
            for (int i = 0; i < lines.length; i++) {
                lines[i] = new StringBuffer("                    ");
            }
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setConfigParameters(DeviceConfigParameters parameters) {
        try {
            getDevice().setConfig(parameters.getCursor(), parameters.getBlinking());
            this.configParameters = new DeviceConfigParameters(getDevice().getConfig());
            super.getCallback().configurationChanged(this.configParameters);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCustomCharacters(DeviceCustomCharacter... characters) {
        try {
            for (DeviceCustomCharacter character : characters) {
                getDevice().setCustomCharacter(character.getIndex(), character.getPixels());
                this.customCharacters.add(new DeviceCustomCharacter(character.getIndex(), getDevice().getCustomCharacter(character.getIndex())));
            }
            super.getCallback().customCharactersChanged(this.customCharacters.toArray(new DeviceCustomCharacter[0]));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDefaultText(DeviceDefaultText... texts) {
        try {
            for (DeviceDefaultText text : texts) {
                getDevice().setDefaultText(text.getLine(), text.getText());
                this.defaultTexts.add(new DeviceDefaultText(text.getLine(), getDevice().getDefaultText(text.getLine())));
            }
            super.getCallback().defaultTextsChanged(this.defaultTexts.toArray(new DeviceDefaultText[0]));
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDefaultTextCounter(Integer counter) {
        try {

            getDevice().setDefaultTextCounter(counter);
            this.defaultTextCounter = getDevice().getDefaultTextCounter();
            super.getCallback().defaultTextCounterChanged(this.defaultTextCounter);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(DeviceWriteLine... lines) {
        try {
            for (DeviceWriteLine line : lines) {
                this.lines[line.getLine()].replace(line.getPosition(), line.getPosition() + line.getText().length(), line.getText());
                writer.readyToWrite(line.getLine());
            }
        } catch (Exception ex) {
            Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class Writer implements Runnable {

        private final BlockingDeque<Short> publishingQueue;

        public Writer() {
            this.publishingQueue = new LinkedBlockingDeque<>();
        }

        public synchronized void readyToWrite(short line) {
            if (this.publishingQueue.contains(line)) {
                return;
            }
            this.publishingQueue.add(line);
            return;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        short line = publishingQueue.take();
                        getDevice().writeLine(line, (short) 0, utf16ToKS0066U(lines[line].toString()));

                    } catch (TimeoutException | NotConnectedException ex) {
                        Logger.getLogger(LCD20x4Device.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (InterruptedException ex) {
                //That is fine, we just stop the writer...
            }
        }
    }

    // Maps a normal UTF-16 encoded string to the LCD charset
    // This has been copied from somewhere else... But I cannot remember from
    // where... Today it can be found @Tinkerforge as well
    private String utf16ToKS0066U(final String utf16) {
        String ks0066u = "";
        char c;

        for (int i = 0; i < utf16.length(); i++) {
            final int codePoint = utf16.codePointAt(i);

            if (Character.isHighSurrogate(utf16.charAt(i))) {
                // Skip low surrogate
                i++;
            }

            // ASCII subset from JIS X 0201
            if ((codePoint >= 0x0020) && (codePoint <= 0x007e)) {
                // The LCD charset doesn't include '\' and '~', use similar
                // characters instead
                switch (codePoint) {
                    case 0x005c:
                        c = (char) 0xa4;
                        break; // REVERSE SOLIDUS maps to IDEOGRAPHIC COMMA
                    case 0x007e:
                        c = (char) 0x2d;
                        break; // TILDE maps to HYPHEN-MINUS
                    default:
                        c = (char) codePoint;
                        break;
                }
            } // Katakana subset from JIS X 0201
            else if ((codePoint >= 0xff61) && (codePoint <= 0xff9f)) {
                c = (char) (codePoint - 0xfec0);
            } // Special characters
            else {
                switch (codePoint) {
                    case 0x00a5:
                        c = (char) 0x5c;
                        break; // YEN SIGN
                    case 0x2192:
                        c = (char) 0x7e;
                        break; // RIGHTWARDS ARROW
                    case 0x2190:
                        c = (char) 0x7f;
                        break; // LEFTWARDS ARROW
                    case 0x00b0:
                        c = (char) 0xdf;
                        break; // DEGREE SIGN maps to KATAKANA SEMI-VOICED SOUND
                    // MARK
                    case 0x03b1:
                        c = (char) 0xe0;
                        break; // GREEK SMALL LETTER ALPHA
                    case 0x00c4:
                        c = (char) 0xe1;
                        break; // LATIN CAPITAL LETTER A WITH DIAERESIS
                    case 0x00e4:
                        c = (char) 0xe1;
                        break; // LATIN SMALL LETTER A WITH DIAERESIS
                    case 0x00df:
                        c = (char) 0xe2;
                        break; // LATIN SMALL LETTER SHARP S
                    case 0x03b5:
                        c = (char) 0xe3;
                        break; // GREEK SMALL LETTER EPSILON
                    case 0x00b5:
                        c = (char) 0xe4;
                        break; // MICRO SIGN
                    case 0x03bc:
                        c = (char) 0xe4;
                        break; // GREEK SMALL LETTER MU
                    case 0x03c2:
                        c = (char) 0xe5;
                        break; // GREEK SMALL LETTER FINAL SIGMA
                    case 0x03c1:
                        c = (char) 0xe6;
                        break; // GREEK SMALL LETTER RHO
                    case 0x221a:
                        c = (char) 0xe8;
                        break; // SQUARE ROOT
                    case 0x00b9:
                        c = (char) 0xe9;
                        break; // SUPERSCRIPT ONE maps to SUPERSCRIPT (minus) ONE
                    case 0x00a4:
                        c = (char) 0xeb;
                        break; // CURRENCY SIGN
                    case 0x00a2:
                        c = (char) 0xec;
                        break; // CENT SIGN
                    case 0x2c60:
                        c = (char) 0xed;
                        break; // LATIN CAPITAL LETTER L WITH DOUBLE BAR
                    case 0x00f1:
                        c = (char) 0xee;
                        break; // LATIN SMALL LETTER N WITH TILDE
                    case 0x00d6:
                        c = (char) 0xef;
                        break; // LATIN CAPITAL LETTER O WITH DIAERESIS
                    case 0x00f6:
                        c = (char) 0xef;
                        break; // LATIN SMALL LETTER O WITH DIAERESIS
                    case 0x03f4:
                        c = (char) 0xf2;
                        break; // GREEK CAPITAL THETA SYMBOL
                    case 0x221e:
                        c = (char) 0xf3;
                        break; // INFINITY
                    case 0x03a9:
                        c = (char) 0xf4;
                        break; // GREEK CAPITAL LETTER OMEGA
                    case 0x00dc:
                        c = (char) 0xf5;
                        break; // LATIN CAPITAL LETTER U WITH DIAERESIS
                    case 0x00fc:
                        c = (char) 0xf5;
                        break; // LATIN SMALL LETTER U WITH DIAERESIS
                    case 0x03a3:
                        c = (char) 0xf6;
                        break; // GREEK CAPITAL LETTER SIGMA
                    case 0x03c0:
                        c = (char) 0xf7;
                        break; // GREEK SMALL LETTER PI
                    case 0x0304:
                        c = (char) 0xf8;
                        break; // COMBINING MACRON
                    case 0x00f7:
                        c = (char) 0xfd;
                        break; // DIVISION SIGN

                    default:
                    case 0x25a0:
                        c = (char) 0xff;
                        break; // BLACK SQUARE
                }
            }

            // Special handling for 'x' followed by COMBINING MACRON
            if (c == (char) 0xf8) {
                if (!ks0066u.endsWith("x")) {
                    c = (char) 0xff; // BLACK SQUARE
                }

                if (ks0066u.length() > 0) {
                    ks0066u = ks0066u.substring(0, ks0066u.length() - 1);
                }
            }

            ks0066u += c;
        }

        return ks0066u;
    }
}
