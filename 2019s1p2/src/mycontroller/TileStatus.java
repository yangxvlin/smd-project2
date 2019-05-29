package mycontroller;

/**
 * Xulin Yang, 904904
 *
 * @create 2019-05-21 23:31
 * description: customized tile status in map
 **/


public enum TileStatus{
    /*UNREACHABLE indicates tiles that cannot move on, i.e. Wall
    * EXPLORED indicates tiles that has been explored
    * UNEXPLORED indicates tiles that has not been explored
    * */
    UNREACHABLE, EXPLORED, UNEXPLORED
}
