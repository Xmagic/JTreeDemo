import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

class TreeNodeTransferable implements Transferable {
	static DataFlavor localObjectFlavor;

	static {
		try {

			localObjectFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	static DataFlavor[] supportedFlavors = { localObjectFlavor };


	Object object;

	public TreeNodeTransferable(Object o) {
		object = o;
	}

	public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(df))
			return object;
		else
			throw new UnsupportedFlavorException(df);
	}

	public boolean isDataFlavorSupported(DataFlavor df) {
		return (df.equals(localObjectFlavor));
	}

	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}
}
