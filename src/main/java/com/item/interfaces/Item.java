<<<<<<< HEAD
package com.item.interfaces;

public interface Item {


    String name = null;
    String price = null;
}
=======
<<<<<<<< HEAD:src/main/java/com/item/ticket.java
package src.item;
========
package com.item.interfaces;
>>>>>>>> jang-je:src/main/java/com/item/interfaces/Item.java

import src.item.interfaces.Item;

public class ticket implements Item{
    private boolean activate = false;
    public void activatTicket(){
        activate = true;
    }
    /*
    10강 이후 파편(아이템1)
    파편은 모아서 검을 구매

    파괴 방지권(아이템2)

    워프 권(아이템3) -> 15강

    확률 업 아이템? (아이템4)

    확정 업 아이템? (아이템5) 20강 -> 21강
     */

}
>>>>>>> jang-je
