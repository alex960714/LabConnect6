package Connect6;


/**
* Connect6/ServerCorbOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/appcorba/Connect6.idl
* 25 ������ 2017 �. 2:13:33 MSK
*/

public interface ServerCorbOperations 
{
  int getColor ();
  int getWinner ();
  void setMove (int player, int[] _changes);
  int getMove ();
  int[] getChanges ();
} // interface ServerCorbOperations
