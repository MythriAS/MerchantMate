![image](https://github.com/user-attachments/assets/b9ac9606-8fdf-4a50-9017-a48a7feb1745)

Merchant Mate is an Android application developed to showcase a shopping cart system where users can browse food items, add them to a cart,
and calculate the total price including tax. The app uses RecyclerView to display a list of food items, and Realm Database for storing and managing the cart data.

# Features

- *Food Item List*: Display a list of food items with their images, names, price, and tax in the first RecyclerView.
- *Add to Cart*: When the user clicks the "Add" button, the selected item moves from the first RecyclerView to the second RecyclerView.
- *Cart Management*: The cart in the second RecyclerView shows item details such as itemimage, itemname, itemquantity, and a delete button.
- *Quantity Management*: Items in the cart can have their quantity increased by clicking the "Add" button, or decreased if multiple items are in the cart.
- *Price Calculation*: The total value of the cart is updated dynamically, based on item price and tax. The total value is the sum of each item’s price + tax.
- *Database Storage*: Realm Database is used for inserting items into the cart and retrieving the list of items from the cart.
- *Delete Items*: On clicking the delete button, the item is either removed from the cart or its quantity is reduced depending on how many items are in the cart.

# Technologies Used

- *Android SDK*: Native Android development.
- *RecyclerView*: To display the food item list and cart items.
- *Realm Database*: Used for storing and querying the cart data.
- *Interface for Communication*: Used to communicate between the RecyclerView and the activity.

# How it works

1. *First RecyclerView (Food Items List)*:
   - Displays a list of food items with image, name, price, and tax.
   - Each item has an "Add" button. When clicked, the item is added to the cart and moved to the second RecyclerView.

2. *Second RecyclerView (Shopping Cart)*:
   - Displays the added items with image, name, and quantity.
   - Items have a delete button:
     - If only one item is in the cart, clicking delete will remove it.
     - If more than one item is present, the quantity is reduced by one.
   - The total value of the cart is calculated and displayed based on the price + tax of each item.

3. *Realm Database*:
   - Data is saved in Realm when items are added to the cart.
   - The data is retrieved when needed to update the UI.

4. *Cart Price Calculation*:
   - The total price is the sum of the item prices and their corresponding taxes.
   - Every time an item is added or removed, the total price is updated dynamically.
