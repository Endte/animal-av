
	package gfgaa.gui.parser.event;

	import gfgaa.gui.parser.ParserActionInterface;
import gfgaa.gui.parser.ParserUnit;

import java.io.StreamTokenizer;
//Madieha
	public class Event_ReadStartKnoten implements ParserActionInterface{

		@Override
		public boolean execute(StreamTokenizer tok, ParserUnit data) {
			data.state = ParserUnit.STATE_LOADING_StartKnoten;
		    data.cVal[0]=0;
		    
		    data.give++;
	        return true;
		    
			
		}
		

	}


