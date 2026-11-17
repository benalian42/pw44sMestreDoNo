//gerar chave ao iniciar 
const CART_STORAGE_KEY = 'mestreDoNoCart';

const cart = {
  items: [],
  _listeners: [],

  init() {
    const storedCart = localStorage.getItem(CART_STORAGE_KEY);
    if (storedCart) {
      this.items = JSON.parse(storedCart);
    }
  },

  _save() {
    localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.items));
    this._notifyListeners();
  },

  add(item) {
    const existingItem = this.items.find(i => i.id === item.id);
    if (existingItem) {
      existingItem.quantity++;
    } else {
      this.items.push({ ...item, quantity: 1 });
    }
    this._save();
  },

  remove(itemId) {
    this.items = this.items.filter(item => item.id !== itemId);
    this._save();
  },

  decreaseQuantity(itemId) {
    const existingItem = this.items.find(i => i.id === itemId);
    if (existingItem) {
      existingItem.quantity--;
      if (existingItem.quantity <= 0) {
        this.items = this.items.filter(item => item.id !== itemId);
      }
    }
    this._save();
  },

  clear() {
    this.items = [];
    this._save();
  },

  get() {
    return this.items;
  },

  getTotalItems() {
    return this.items.reduce((total, item) => total + item.quantity, 0);
  },

  subscribe(listener) {
    this._listeners.push(listener);
    return () => {
      this._listeners = this._listeners.filter(l => l !== listener);
    };
  },

  _notifyListeners() {
    this._listeners.forEach(listener => listener());
  }
};

cart.init();

export default cart;
