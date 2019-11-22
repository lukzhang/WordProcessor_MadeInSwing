public class WriteControllerXML {

    ReadWriteXML readWriteXML = new ReadWriteXML();

    public void saveCopy(String docName) throws Exception {
        //Check if file exists, if not create a new XML entry. If so, update the nonce
        if(!readWriteXML.containsFileXML(docName)){
            readWriteXML.appendXML();
        } else{
            readWriteXML.incrementNonceXML(docName);
        }
    }

    public int highestDocNonce(String docName){
        return 0;
    }

}
