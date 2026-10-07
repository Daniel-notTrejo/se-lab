package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedo stores and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500() {
    this.primaryTorpedoStore = new TorpedoStore(10);
    this.secondaryTorpedoStore = new TorpedoStore(10);
  }

  public boolean fireLaser(FiringMode firingMode) {
    
    //solution of the lazy student
    return false;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty, the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedo(FiringMode firingMode) {
    switch (firingMode) {
      case SINGLE:
        if (wasPrimaryFiredLast) {
          // firing the secondary store first if possible otherwise the primary
          return fireSingleTorpedo(secondaryTorpedoStore, primaryTorpedoStore, false);
        }
        else {
          // try to fire the primary first otherwise the secondary
          return fireSingleTorpedo(primaryTorpedoStore, secondaryTorpedoStore, true);
        }

      case ALL:
        // try to fire both of the torpedo stores
        boolean primarySuccess = false;
        boolean secondarySuccess = false;

        if (! primaryTorpedoStore.isEmpty()) {
          primarySuccess = primaryTorpedoStore.fire(1);
        }
        if (! secondaryTorpedoStore.isEmpty()) {
          secondarySuccess = secondaryTorpedoStore.fire(1);
        }
        return primarySuccess || secondarySuccess;
      default:
        return false;
    }
  }


    // Single fire mode, reducing complexity by splitting the logic into a separate method
    private boolean fireSingleTorpedo(TorpedoStore firstStore, TorpedoStore secondStore, boolean isPrimaryFiredLast){
      if (! firstStore.isEmpty()) {
        // fire the first store and update the last fired store
        wasPrimaryFiredLast = isPrimaryFiredLast;
        return firstStore.fire(1);
      } 
      else if (! secondStore.isEmpty()) {
        // fire the second store and update the last fired store
        wasPrimaryFiredLast = !isPrimaryFiredLast;
        return secondStore.fire(1);
      } 
      return false;
    }

}
