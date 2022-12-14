package simulation;

/**
 *	Blueprint for accepting products
 *	Classes that implement this interface can accept products
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public interface IProductAcceptor
{
	/**
	*	Method to have this object process an event
	*	@param p	The product that is accepted
        *       @return true if accepted
	*/
	public boolean receiveProduct(Product p);
}
