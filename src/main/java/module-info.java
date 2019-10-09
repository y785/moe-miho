module moe.maple.miho {
    requires java.desktop; // Awt for drawing

    exports moe.maple.miho.foothold;
    exports moe.maple.miho.line;
    exports moe.maple.miho.point;
    exports moe.maple.miho.rect;
    exports moe.maple.miho.space;
    exports moe.maple.miho.tree;
    exports moe.maple.miho.util;

    exports moe.maple.miho.space.bst;
    exports moe.maple.miho.tree.bst;
}