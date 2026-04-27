public class BinarySearchTree {

    Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(Player player) {
        root = insert(root, player);
    }

    private Node insert(Node current, Player player) {
        if (current == null) {
            return new Node(player);
        }
        if (player.getRanking() < current.player.getRanking()) {
            current.left = insert(current.left, player);
        } else if (player.getRanking() > current.player.getRanking()) {
            current.right = insert(current.right, player);
        }
        return current;
    }

    public boolean search(String name) {
        return search(root, name) != null;
    }

    private Node search(Node current, String name) {
        if (current == null) {
            return null;
        }
        if (current.player.getNickname().equalsIgnoreCase(name)) {
            return current;
        }
        Node leftResult = search(current.left, name);
        if (leftResult != null) {
            return leftResult;
        }
        return search(current.right, name);
    }

    public Player remove(String name) {
        Node target = search(root, name);
        if (target == null) {
            return null;
        }
        Player removed = target.player;
        root = remove(root, target.player.getRanking());
        return removed;
    }

    private Node remove(Node current, int ranking) {
        if (current == null) {
            return null;
        }
        if (ranking < current.player.getRanking()) {
            current.left = remove(current.left, ranking);
        } else if (ranking > current.player.getRanking()) {
            current.right = remove(current.right, ranking);
        } else {
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }
            Node successor = findMin(current.right);
            current.player = successor.player;
            current.right = remove(current.right, successor.player.getRanking());
        }
        return current;
    }

    private Node findMin(Node current) {
        if (current.left == null) {
            return current;
        }
        return findMin(current.left);
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node current) {
        if (current == null) {
            return;
        }
        inOrder(current.left);
        System.out.println(current.player.getNickname() + " - " + current.player.getRanking());
        inOrder(current.right);
    }

    public Node getRoot() {
        return root;
    }
}