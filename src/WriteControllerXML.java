public class WriteControllerXML {

    ReadWriteXML readWriteXML = new ReadWriteXML();

    public void saveCopy(String docName) throws Exception {
        //Check if file exists, if not create a new XML entry. If so, update the nonce
        if(!readWriteXML.containsFileXML(docName)){
            readWriteXML.appendXML(docName);
        } else{
            System.out.println("INCREMENTED "+docName);
            readWriteXML.incrementNonceXML(docName);
        }
    }

    //Returns whatever the highest nonce is at the moment
    public int highestDocNonce(String docName){
        return readWriteXML.highestNonce(docName);
    }

}
