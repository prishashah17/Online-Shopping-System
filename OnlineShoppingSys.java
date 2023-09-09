

import java.util.*;

public class OnlineShoppingSys {
    public static void main(String[] args) {
        Admin beff = new Admin("Prisha","123456");
        Admin.admin.add(beff);
        Enter_App enter_app = new Enter_App();
        enter_app.enter_menu();

    }
}

class Enter_App{
    public void enter_menu(){
        System.out.println("""
                WELCOME TO ONLINE SHOPPING SYSTEM
                1) Enter as Admin
                2) Explore the Product Catalog
                3) Show Available Deals
                4) Enter as Customer
                5) Exit the Application""");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter choice: ");
        int ch = sc.nextInt();
        if (ch==1) {
            Scanner sc1 = new Scanner(System.in);
            System.out.println("Enter username: ");
            String username = sc1.nextLine();
            System.out.println("Enter password: ");
            String password = sc1.nextLine();
            for (Admin admin : Admin.admin) {
                if (Objects.equals(admin.getName(), username) && Objects.equals(admin.getPassword(), password)) {
                    admin.enter_admin();
                }
                else {
                    System.out.println("INVALID CREDENTIALS");
                    enter_menu();
                }
            }
        }
        else if (ch==2) {
            Customer c = new Customer();
            c.browse_prod();
            enter_menu();
        } else if (ch==3) {
            Customer c1 = new Customer();
            c1.browse_deals();
            enter_menu();
        } else if (ch==4) {
            Customer c = new Customer();
            c.enter_customer();
        }else {
            System.out.println("Thank you for using FLIPZON");
        }

    }

}


class Admin {
    private String name ;
    private String password;
    public static ArrayList <Category> categories = new ArrayList<>();
    public static ArrayList <Products> deals = new ArrayList<>();
    public static ArrayList<Admin> admin = new ArrayList<>();

    Admin(){}
    Admin(String name,String password){
        this.name=name;
        this.password=password;
    }

    public String getPassword() {return password;}
    public String getName() {return name;}

    public void enter_admin(){
        System.out.println("Welcome "+this.getName()+"\n"+
                """
                Please choose any one of the following actions:
                1) Add category
                2) Delete category
                3) Add Product
                4) Delete Product
                5) Set Discount on Product
                6) Add giveaway deal
                7) Back""");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter choice: ");
        int ch = sc.nextInt();
        if(ch==1) {
            add_category();
            enter_admin();
        }
        else if (ch==2) {
            delete_category();
            enter_admin();
            }
        else if (ch==3) {
            add_product();
            enter_admin();
        }
        else if (ch==4) {
            delete_product();
        }
        else if (ch==5) {
            set_discount();
            enter_admin();
        }
        else if (ch==6) {
            giveaway_Deals();
            enter_admin();}
        else if (ch==7) {
            Enter_App enter_app =new Enter_App();
            enter_app.enter_menu();
        }else {System.out.println("WRONG INPUT");}

    }
    public void add_category(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter category ID: ");
        int cID = sc.nextInt();
        sc.nextLine();
        boolean flag = false;
        for (Category category: categories){
            if(category.getcID()==cID){
                flag=true;
                System.out.println("This ID already exists. Please choose a unique one.");
                add_category();
                break;
            }
        }
        if(!flag){
            System.out.println("Enter category name : ");
            String cname = sc.nextLine();
            Category c = new Category(cID,cname);
            c.add_prod();
            categories.add(c);

        }
    }

    public void delete_category(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter category ID: ");
        int cID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter category Name: ");
        String cname = sc.nextLine();
        for (Category c : categories){
            if((c.getcID()==cID) && (Objects.equals(c.getCname(), cname))){
                categories.remove(c);
            }
        }
    }
    public void add_product(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter category ID: ");
        int cID = sc.nextInt();
        boolean flag = false;
        for (Category c: categories){
            if(c.getcID()==cID){
                flag=true;
                c.add_prod();

            }
        }
        if(!flag){
            System.out.println("Category does not exist. Please create it first.");
        }
    }
    public void delete_product(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter category name: ");
        String cname=sc.nextLine();
        System.out.println("Enter product ID");
        float pID = sc.nextFloat();
        sc.nextLine();
        boolean flag = false;
        boolean flag2= false;
        for (Category c : categories){
            if (Objects.equals(c.getCname(), cname)){
                flag = true;
                for (Products p :c.productsList){
                    if (p.getpID()==pID) {
                        flag2=true;
                        c.productsList.remove(p);
                        if (c.productsList.isEmpty()) {
                            System.out.println("Category is empty");
                            System.out.println("Please choose: ");
                            System.out.println("1.Add new product.");
                            System.out.println("2.Delete category.");
                            System.out.println("Enter choice: ");
                            int ch = sc.nextInt();
                            if (ch == 1) {
                                c.add_prod();
                                enter_admin();
                            } else if (ch == 2) {
                                categories.remove(c);
                                enter_admin();
                            }else {
                                System.out.println("wrong input");
                                enter_admin();
                            }
                        }
                        else {
                            enter_admin();
                        }

                    }
                }
            }
        }
        if(!flag){
            System.out.println("Category does not exist");
            enter_admin();
        }
        else if(!flag2){
            System.out.println("Product does not exist");
            enter_admin();
        }


    }
    public void set_discount(){
        System.out.println("Enter Product ID : ");
        Scanner sc = new Scanner(System.in);
        float pID = sc.nextFloat();
        sc.nextLine();
        boolean check = false;
        for (Category category:categories) {
            for (Products products : category.productsList) {
                if (products.getpID() == pID) {
                    check=true;
                    System.out.println("Enter discount for each category : (ELITE/PRIME/NORMAL)");
                    String in = sc.nextLine();
                    String[] res = in.split(" ");
                    products.normal_dis = Integer.parseInt(res[2]);
                    products.prime_dis = Integer.parseInt(res[1]);
                    products.elite_dis = Integer.parseInt(res[0]);

                }
            }
        }
        if (!check){
            System.out.println("Product not found");
        }
    }

    public void giveaway_Deals(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter product ID 1: ");
        float pID1 = sc.nextFloat();
        sc.nextLine();
        System.out.println("Enter product ID 2: ");
        float pID2 = sc.nextFloat();
        sc.nextLine();
        System.out.println("Enter price for each category : (ELITE/PRIME/NORMAL)");
        String in = sc.nextLine();
        String[] res = in.split(" ");
        boolean flag = false;
        boolean flag1=false;
        for (Category c : categories){
            for (Products p : c.productsList){
                if (p.getpID()==pID1){
                    flag=true;
                    for (Products p1 : c.productsList){
                        if(p1.getpID()==pID2) {
                            flag1=true;
                            if (p.getPrice() + p1.getPrice() > Integer.parseInt(res[2]) && p.getPrice() + p1.getPrice() > Integer.parseInt(res[1]) && p.getPrice() + p1.getPrice() > Integer.parseInt(res[0]) ) {
                                String name = p.getPname()+" & "+p1.getPname();
                                int pID =Math.round(p.getpID()+p1.getpID()) ;
                                String product_Dets = p.getProduct_details()+"\n"+p1.getProduct_details();
                                double price =p.getPrice()+p1.getPrice();
                                Products deal_p = new Products(name,pID,product_Dets,price);
                                deal_p.normal_deal = Integer.parseInt(res[2]);
                                deal_p.prime_deal = Integer.parseInt(res[1]);
                                deal_p.elite_deal = Integer.parseInt(res[0]);
                                deals.add(deal_p);
                            }
                            else {
                                System.out.println("Discounted Price should be less than combined price");
                            }
                        }
                    }
                }
            }
        }
        if(!flag){
            System.out.println("Product 1 not found");
            enter_admin();
        } else if (!flag1) {
            System.out.println("Product 2 not found");
        }
    }
}

class Products{
    private String pname;
    private float pID;
    private double price;
    private String product_details;
    private int quantity=0;

    int normal_dis=0;
    int elite_dis=0;
    int prime_dis=0;

    int normal_deal=0;
    int elite_deal=0;
    int prime_deal=0;


    Products(String pname,float pID,String product_details,double price){
        this.pID=pID;
        this.product_details=product_details;
        this.pname=pname;
        this.price=price;
    }

    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setPrice(double price) {this.price = price;}

    public String getPname() {return pname;}
    public float getpID() {return pID;}
    public String getProduct_details() {return product_details;}
    public double getPrice() {return price;}
    public int getQuantity() {return quantity;}
}
class Category{
    private int cID;
    private String cname;
    ArrayList<Products> productsList = new ArrayList<>();

    Category(int cID,String cname){
        this.cID=cID;
        this.cname=cname;
    }
    public int getcID() {return cID;}
    public String getCname() {return cname;}

    public void add_prod(){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter Product Name: ");
        String pname = sc.nextLine();
        System.out.println("Enter Product ID");
        float pID = sc.nextFloat();
        sc.nextLine();
        boolean check = false;
        for (Category category: Admin.categories){
            for (Products products:category.productsList){
                if (products.getpID()==pID){
                    check=true;
                    System.out.println("Product ID should be unique");
                    add_prod();
                    break;
                }
            }
        }
        if (!check) {
            System.out.println("Enter product price: ");
            double price = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter product details: ");
            String product_dets = sc.nextLine();
            System.out.println("Enter Product Quantity: ");
            int pquantity = sc.nextInt();
            sc.nextLine();
            Products p = new Products(pname, pID, product_dets, price);
            p.setQuantity(pquantity);
            productsList.add(p);
        }
    }
}




class Customer{
    private String name;
    private int age;
    private int phone_no;
    private String email;
    private String password;
    private double wallet = 1000;


    public static ArrayList<Customer> reg_customers = new ArrayList<>();
    ArrayList<Products> cart = new ArrayList<>();
    ArrayList<Integer> coupouns = new ArrayList<>();

    Customer(){}
    Customer(String name,int age,int phone_no,String email,String password){
        this.name=name;
        this.age=age;
        this.phone_no= phone_no;
        this.email=email;
        this.password=password;
    }

    public void setWallet(double wallet){this.wallet = wallet;}


    public int getAge() {return age;}
    public int getPhone_no() {return phone_no;}
    public String getEmail() {return email;}
    public String getName() {return name;}
    public String getPassword() {return password;}
    public double getWallet() {return wallet;}


    public void enter_customer(){
        System.out.println("""
                1) Sign up
                2) Log in
                3) Back""");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter choice: ");
        int ch = sc.nextInt();
        if(ch==1) {
            sign_up();
            enter_customer();
        }
        else if (ch==2) {log_in();}
        else if (ch==3) {
            System.out.println("Bye");
            Enter_App enter_app=new Enter_App();
            enter_app.enter_menu();
        }
    }
    public void sign_up(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name=sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        System.out.println("Enter age: ");
        int age=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Phone no: ");
        int ph= sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Email: ");
        String email=sc.nextLine();
        Customer c = new Customer(name,age,ph,email,password);
        reg_customers.add(c);
    }

    public void log_in(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name=sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        boolean flag=true;
        boolean flag1=false;
        for (Customer customer : reg_customers){
            if(Objects.equals(customer.getName(), name) && Objects.equals(customer.getPassword(), password)) {
                flag1=true;
                while (flag) {
                    System.out.println("Welcome " + customer.getName() +"\n"+
                            """
                                    1) browse products
                                    2) browse deals
                                    3) add a product to cart
                                    4) add products in deal to cart
                                    5) view coupons
                                    6) check account balance
                                    7) view cart
                                    8) empty cart
                                    9) checkout cart
                                    10) upgrade customer status
                                    11) Add amount to wallet
                                    12) back""");
                    System.out.println("Enter choice: ");
                    int ch = sc.nextInt();
                    if (ch == 1) {
                        browse_prod();

                    } else if (ch == 2) {
                        browse_deals();

                    } else if (ch == 3) {
                        customer.add_to_cart();

                    } else if (ch == 4) {
                        customer.add_deal_cart();

                    } else if (ch == 5) {
                        customer.view_coupons();

                    } else if (ch == 6) {
                        customer.getBalance();

                    } else if (ch == 7) {
                        customer.view_cart();
                    } else if (ch == 8) {
                        customer.empty_cart();
                    } else if (ch == 9) {
                        customer.check_out();
                    } else if (ch == 10) {
                        customer.upgrade_status();
                    } else if (ch == 11) {
                        customer.add_amount();
                    } else if (ch == 12) {
                        enter_customer();
                        flag=false;
                    } else {
                        System.out.println("WRONG INPUT");
                        flag=false;
                    }
                }
            }
        }
        if (!flag1){
            System.out.println("Customer not found.");
            enter_customer();
        }
    }
    public void browse_prod(){
        if (!Admin.categories.isEmpty()) {
            for (Category category : Admin.categories) {
                System.out.println(category.getCname() + ":");
                for (Products p : category.productsList) {
                    System.out.println("Product Name: " + p.getPname());
                    System.out.println("Product ID: " + p.getpID());
                    System.out.println("Product Price: " + p.getPrice());
                    System.out.println("Product Details: " + p.getProduct_details());
                    System.out.println("\n");
                }
            }
        }
        else {
            System.out.println("NO PRODUCTS FOR NOW");
        }
    }
    public void browse_deals() {
        if (!Admin.deals.isEmpty()) {
            for (Products products : Admin.deals) {
                System.out.println("Product name: " + products.getPname());
                System.out.println("Product ID : " + products.getpID());
                System.out.println("Product Original price: " + products.getPrice());
                System.out.println("Product deal price (Elite) : "+String.valueOf(products.elite_deal));
                System.out.println("Product deal price (Prime) : "+String.valueOf(products.prime_deal));
                System.out.println("Product deal price (Normal) : "+String.valueOf(products.normal_deal));
            }
        }
        else {
            System.out.println("NO DEALS AVAILABLE RIGHT NOW");
        }
    }
    public void add_to_cart(){
        for (Customer customer : reg_customers) {
            if (Objects.equals(this.getName(), customer.getName()) && Objects.equals(this.getPassword(), customer.getPassword())) {
                System.out.println("Enter product ID: ");
                Scanner sc = new Scanner(System.in);
                float pID = sc.nextFloat();
                sc.nextLine();
                System.out.println("Enter quantity: ");
                int quantity = sc.nextInt();
                boolean flag = false;
                for (Category category : Admin.categories) {
                    for (Products product : category.productsList) {
                        if (product.getpID() == pID) {
                            flag = true;
                            if (quantity <= product.getQuantity()) {
                                String name = product.getPname();
                                float pID1 = product.getpID();
                                String product_deets = product.getProduct_details();
                                double price = quantity * product.getPrice();
                                Products cart_product = new Products(name, pID1, product_deets, price);
                                customer.cart.add(cart_product);
                                cart_product.setQuantity(quantity);
                                System.out.println("PRODUCT ADDED ");
                            }
                            else {
                                System.out.println("Product not available");
                            }
                        }
                    }
                }

                if (!flag) {
                    System.out.println("Product ID not found.");
                }
            }
        }
    }
    public void add_deal_cart(){
        for (Customer customer : reg_customers) {
            if (Objects.equals(this.getName(), customer.getName()) && Objects.equals(this.getPassword(), customer.getPassword())) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter product - deal ID : ");
                float pID = sc.nextFloat();
                sc.nextLine();
                boolean flag = false;
                for (Products product : Admin.deals) {
                    if (product.getpID() == pID) {
                        flag = true;
                        if (customer.getClass()==Elite.class){
                            product.setPrice(product.elite_deal);
                            customer.cart.add(product);
                            System.out.println("PRODUCT ADDED ");
                        } else if (customer.getClass()==Prime.class) {
                            product.setPrice(product.prime_deal);
                            customer.cart.add(product);
                            System.out.println("PRODUCT ADDED ");
                        }else {
                            product.setPrice(product.normal_deal);
                            customer.cart.add(product);
                            System.out.println("PRODUCT ADDED ");
                        }
                    }
                }
                if (!flag) {
                    System.out.println("Product not found");
                }
            }
        }
    }
    public void view_coupons(){
        for (Customer customer : reg_customers) {
            if (Objects.equals(this.getName(), customer.getName()) && Objects.equals(this.getPassword(), customer.getPassword())) {
                if (customer.coupouns.isEmpty()) {
                    System.out.println("You have no coupons");
                } else {
                    System.out.println("You have coupons worth : ");
                    for (Integer integer : customer.coupouns) {
                        System.out.println(integer + " %");
                    }
                }
            }
        }
    }
    public void getBalance(){
        System.out.println("Current amount balance is : ");
        for (Customer customer:reg_customers){
            if (Objects.equals(customer.getName(), this.getName()) && customer.getPhone_no()==this.getPhone_no()){
                System.out.println("Rs "+customer.getWallet());
            }
        }
    }
    public void view_cart(){
        for (Customer customer : reg_customers) {
            if (Objects.equals(this.getName(), customer.getName()) && Objects.equals(this.getPassword(), customer.getPassword())) {
                for (Products p : customer.cart) {
                    System.out.println("Product name: " + p.getPname());
                    System.out.println("Product ID: " + p.getpID());
                    System.out.println("Product price: " + p.getPrice());
                    System.out.println("Product details: " + p.getProduct_details());
                }
            }
        }
    }
    public void empty_cart(){
        for (Customer customer : reg_customers) {
            if (Objects.equals(this.getName(), customer.getName()) && Objects.equals(this.getPassword(), customer.getPassword())) {
                if (customer.cart.isEmpty()) {
                    System.out.println("Cart is already Empty");
                } else {
                    customer.cart.clear();
                    System.out.println("Cart emptied");
                }
            }
        }
    }
    public void check_out(){
        System.out.println("Details - ");
        double total_cost=0;
        double discount_product_price = 0;
        double bill=0;
        boolean check =false;
        for (Customer customer:reg_customers) {
            if (Objects.equals(customer.getName(), this.getName()) && Objects.equals(customer.getPassword(), this.getPassword())) {
                for (Products cart_prod : customer.cart) {
                    if (cart_prod.getPname().contains("&"))
                    {
                        if (customer.getClass() == Elite.class) {
                            total_cost = total_cost + cart_prod.elite_deal;
                        } else if (customer.getClass() == Prime.class) {
                            total_cost = total_cost + cart_prod.prime_deal;
                        } else {
                            total_cost = total_cost + cart_prod.normal_deal;
                        }
                    }
                    else
                    {
                        total_cost = total_cost + cart_prod.getPrice();
                        if (customer.getClass() == Elite.class) {
                            int dis = Math.max(((Elite) customer).get_discount(), cart_prod.elite_dis);
                            discount_product_price = discount_product_price + (cart_prod.getPrice() * dis * 0.01);
                        } else if (customer.getClass() == Prime.class) {
                            int dis = Math.max(((Prime) customer).get_discount(), cart_prod.prime_dis);
                            discount_product_price = discount_product_price + (cart_prod.getPrice() * dis * 0.01);
                        }
                    }
                }
                if (total_cost - discount_product_price < customer.getWallet()) {
                    check=true;
                    for (Products cart_prod : customer.cart) {
                        System.out.println("Product name : " + cart_prod.getPname());
                        System.out.println("Product ID : " + cart_prod.getpID());
                        System.out.println("Product details: " + cart_prod.getProduct_details());
                        System.out.println("Product price: " + cart_prod.getPrice());
                        for (Category category:Admin.categories){
                            for (Products product:category.productsList){
                                if (product.getpID()==cart_prod.getpID()){
                                    product.setQuantity(product.getQuantity()-cart_prod.getQuantity());
                                }
                            }
                        }
                    }
                    if (customer.getClass() == Elite.class) {
                        System.out.println("Delivery charges: " + String.valueOf(((Elite) customer).delivery_charges()));
                        System.out.println("Order placed will be delivered in 2 days.");
                        bill=total_cost - discount_product_price + ((Elite) customer).delivery_charges();
                        System.out.println("Total cost : Rs" + String.valueOf(bill));
                        customer.setWallet(customer.getWallet()-bill);
                        customer.empty_cart();
                    } else if (customer.getClass() == Prime.class) {
                        System.out.println("Delivery charges: " + String.valueOf(((Prime) customer).delivery_charges()));
                        System.out.println("Order placed will be delivered in 3-6 days.");
                        bill=total_cost - discount_product_price + ((Prime) customer).delivery_charges();
                        System.out.println("Total cost : Rs" + String.valueOf(bill));
                        customer.setWallet(customer.getWallet()-bill);
                        customer.empty_cart();
                    } else {
                        System.out.println("Delivery charges : " + String.valueOf(customer.normal_deliverycharges()));
                        System.out.println("Order placed will be delivered in 7-10 days.");
                        bill=total_cost - discount_product_price + customer.normal_deliverycharges();
                        System.out.println("Total cost : Rs" + String.valueOf(bill));
                        customer.setWallet(customer.getWallet()-bill);
                        customer.empty_cart();
                    }

                    if (bill > 5000) {
                        if (customer.getClass() == Elite.class) {
                            ((Elite) customer).coupons();
                            System.out.println("YOU GOT COUPONS WORTH : ");
                            for (Integer integer : customer.coupouns) {
                                System.out.println(String.valueOf(integer) + " %");
                            }
                        } else if (customer.getClass() == Prime.class) {
                            ((Prime) customer).coupons();
                            System.out.println("YOU GOT COUPONS WORTH : ");
                            for (Integer integer : customer.coupouns) {
                                System.out.println(String.valueOf(integer) + " %");
                            }
                        }
                    }
                }
            }
        }
        if (!check){
            System.out.println("Insufficient balance!");
        }
    }
    public void upgrade_status(){
        for (Customer customer : reg_customers) {
            if (Objects.equals(this.getName(), customer.getName()) && this.getPassword() == customer.getPassword())
            {
                if ((customer.getClass() != Elite.class) && (customer.getClass() != Prime.class)) {
                    System.out.println("CURRENT STATUS : NORMAL");
                    System.out.println(" ");
                    System.out.println("UPGRADE TO: ");
                    System.out.println("1.ELITE");
                    System.out.println("2.PRIME");
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Enter choice: ");
                    int ch = sc.nextInt();
                    if (ch == 1) {
                        String name = customer.getName();
                        int age = customer.getAge();
                        String email = customer.getEmail();
                        String password = customer.getPassword();
                        double wallet = customer.getWallet();
                        int phone_no = customer.getPhone_no();
                        customer.setWallet(customer.getWallet() - 300);
                        Elite elite = new Elite(name, age, phone_no, email, password);
                        elite.setWallet(wallet - 300);
                        if (!customer.cart.isEmpty()) {
                            for (Products products : customer.cart) {
                                elite.cart.add(products);
                                System.out.println("PRODUCT ADDED ");
                            }
                        }
                        reg_customers.remove(customer);
                        reg_customers.add(elite);
                        System.out.println("Status upgraded to ELITE");
                        break;
                    } else if (ch == 2) {
                        String name = customer.getName();
                        int age = customer.getAge();
                        String email = customer.getEmail();
                        String password = customer.getPassword();
                        double wallet = customer.getWallet();
                        int phone_no = customer.getPhone_no();
                        customer.setWallet(customer.getWallet() - 200);
                        Prime prime = new Prime(name, age, phone_no, email, password);
                        prime.setWallet(wallet - 200);
                        if (!customer.cart.isEmpty()) {
                            prime.cart.addAll(customer.cart);
                        }
                        reg_customers.remove(customer);
                        reg_customers.add(prime);
                        System.out.println("Status upgraded to PRIME");
                        break;
                    } else {
                        System.out.println("Wrong Input");
                    }
                } else if (customer.getClass() == Prime.class) {
                    System.out.println("CURRENT STATUS : PRIME");
                    System.out.println("Upgrading status to Elite");
                    String name = customer.getName();
                    int age = customer.getAge();
                    String email = customer.getEmail();
                    String password = customer.getPassword();
                    double wallet = customer.getWallet();
                    int phone_no = customer.getPhone_no();
                    customer.setWallet(customer.getWallet() - 300);
                    Elite elite = new Elite(name, age, phone_no, email, password);
                    elite.setWallet(wallet - 300);
                    if (!customer.cart.isEmpty()) {
                        elite.cart.addAll(customer.cart);
                    }
                    reg_customers.remove(customer);
                    reg_customers.add(elite);
                    System.out.println("Status upgraded to PRIME");
                    break;
                } else {
                    System.out.println("CURRENT STATUS : ELITE");
                    System.out.println("Cannot upgrade further more.");
                    break;
                }
            }
        }
    }
    public void add_amount(){
        for (Customer customer:reg_customers) {
            if (Objects.equals(customer.getName(), this.getName()) && Objects.equals(customer.getPassword(), this.getPassword())) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter amount you want to enter: ");
                double cash = sc.nextDouble();
                sc.nextLine();
                double prevcash = this.getWallet();
                this.setWallet(prevcash + cash);
                System.out.println("Amount added");
            }
        }
    }

    public double normal_deliverycharges(){
        double order_value =0;
        for (Products cart_product : cart){
            for (Category category : Admin.categories){
                for(Products category_prodcut : category.productsList){
                    if(cart_product.getpID()==category_prodcut.getpID()){
                        order_value=order_value+0.05*(category_prodcut.getPrice());
                    }
                }
            }
        }
        return 100+order_value;
    }

}
interface privliages{
    void coupons();
    int get_discount();
    double delivery_charges();
}

class Elite extends Customer implements privliages{
    Elite(String name, int age, int phone_no, String email, String password) {
        super(name, age, phone_no, email, password);
//        float w=this.getWallet();
//        this.setWallet(w-300);
    }

    @Override
    public void coupons(){
        int min =5;
        int max = 15;
        int min1 =3;
        int max1 = 4;
        int c = (int)(Math.random()*(max1-min1+1)+min1);
        for (int i =0;i<c;i++){
            int b = (int)(Math.random()*(max-min+1)+min);
            this.coupouns.add(b);
        }


    }
    public int get_discount() {
        if (this.coupouns.isEmpty()){
            return 10;
        }
        else {
            int x = Collections.max(this.coupouns);
            int y = Math.max(x,10);
            return y;
        }
    }
    public double delivery_charges(){
        return 100;
    };
}

class Prime extends Customer implements privliages{
    Prime(String name, int age, int phone_no, String email, String password) {
        super(name, age, phone_no, email, password);
//        float w=this.getWallet();
//        this.setWallet(w-200);
    }
    @Override
    public void coupons() {
        int min = 5;
        int max = 15;
        int min1 = 1;
        int max1 = 2;
        int c = (int) (Math.random() * (max1 - min1 + 1) + min1);
        for (int i = 0; i < c; i++) {
            int b = (int) (Math.random() * (max - min + 1) + min);
            this.coupouns.add(b);
        }
    }
    public int get_discount() {
        if (this.coupouns.isEmpty()){
            return 5;
        }
        else {
            int x = Collections.max(this.coupouns);
            return Math.max(x,5);
        }
    }

    public double delivery_charges(){
        double order_value = 0;
        for (Customer customer : reg_customers) {
            if (Objects.equals(this.getName(), customer.getName()) && this.getPhone_no() == customer.getPhone_no()) {
                for (Products cart_product : customer.cart) {
                    for (Category category : Admin.categories) {
                        for (Products category_prodcut : category.productsList) {
                            if (cart_product.getpID() == category_prodcut.getpID()) {
                                order_value = order_value + 0.02 * (category_prodcut.getPrice());
                            }
                        }
                    }
                }
            }
        }
        return  100+order_value;
    };


}





