var AddToCartButton = React.createClass({
    handleClick: function(e) {
        e.preventDefault();
        // should probably combine this function with the ajax call down below
        $.ajax({
            type: "PATCH",
            url: "/cart/",
            dataType: "json",
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify({productId: this.props.productId, count: 1})
        })
            .done(function(data) {
                EventSystem.publish("product.added", data.count);
            })
            .fail(function () {
                alert("Could not add item to cart!");
            })
    },
    render: function () {
        return (<a className="btn add-cart" href="#" onClick={this.handleClick}>Add To Cart</a>)
    }
});


var ProductTile = React.createClass({
    render: function () {
        return(
            <div className="product">
                <p className="name">{this.props.product.aggregateDescription}</p>
                <div className="image-container">
                    <img src={this.props.product.imageURL}/>
                </div>
                <p className="sub-description">{this.props.product.inventoryOnHand} Remaining!</p>
                <AddToCartButton productId={this.props.product.id}></AddToCartButton>
            </div>)
    }
});

var ProductList = React.createClass({
    getInitialState: function() {
        return {products: []};
    },
    componentDidMount: function() {
        var self = this;
        $.getJSON(this.props.url)
            .done(function(data) {
                self.setState({products: data});
            });
    },
    render: function () {
        var nodes = <div>No</div>;
        nodes = this.state.products.map(function(product) {
            return (
                <ProductTile key={product.id} product={product}></ProductTile>
                );
        });
        return(<div className="product-list-inner">{nodes}</div>);


    }
});

var ShoppingCartCounter = React.createClass({
    handleClick: function(e) {
        e.preventDefault();
        $("#cartList").toggleClass("active");
    },

    getInitialState: function () {
        return {count: 0};
    },

    componentDidMount: function () {
        var self = this;
        EventSystem.subscribe("product.added", function (count) {
            self.setState({count: count});
        });

        $.getJSON(this.props.url)
            .done(function(data) {
                self.setState({count: data['count']});
            });
    },
    render: function () {
        return (<div className="btn primary shopping-cart-counter" onClick={this.handleClick}>
            {this.state.count} items in cart
        </div>);
    }
});

var ShoppingCartRow = React.createClass({
    updateCart: function (count) {
        var self = this,
            data = {productId: this.props.product.id, count: count};

        return $.ajax({
            type: "PATCH",
            url: this.props.url,
            dataType: "json",
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(data)
        })
            .done(function(data) {
                EventSystem.publish("product.added", data.count);
                self.props.count = self.props.count + count;
            })
            .fail(function () {
                alert("Could not update cart!");
            })
    },

    handleUpdate:function (event, id) {
        var $button = $("#cartList").find("[data-reactid='" + id + "']"),
            count = $button.siblings(".count-input").val() - this.props.count;
        this.updateCart(count);
    },
    handleClear: function (event, id) {
        this.updateCart(-this.props.count)
            .done(function () {
                var $button = $("#cartList").find("[data-reactid='" + id + "']"),
                    $row = $button.parents(".shopping-cart-row");
                $row.remove();
            });
    },

    render: function () {
        return (<div className="shopping-cart-row">
            <div className="description"><img src={this.props.product.imageURL}/>{this.props.product.aggregateDescription} ({this.props.product.sku})</div>
            <div className="controls">
                <input className="count-input" defaultValue={this.props.count}/>
                <a className="btn update" onClick={this.handleUpdate}>Update</a>
                <a className="btn primary clear" onClick={this.handleClear}>Clear</a>
            </div>
        </div>);
    }

});

var ShoppingCartList = React.createClass({
    getInitialState: function() {
        return {items: []};
    },
    componentDidMount: function() {
        var self = this;
        $.getJSON(this.props.url)
            .done(function(data) {
                self.setState({items: data});
            });
    },

    clearCart: function () {
        var self = this;
        if (confirm("Are you sure you wish to clear your cart? There's so much coffee still to buy!")) {
            $.ajax({
                type: "DELETE",
                url: this.props.url,
                dataType: "json",
                contentType:"application/json; charset=utf-8",
                data: JSON.stringify({})
            })
                .done(function(data) {
                    EventSystem.publish("product.added", 0);
                    //self.props.count = self.props.count + count;
                    self.setState({items: []});
                })
                .fail(function () {
                    alert("Could not update cart!");
                })
        }

    },


    render: function () {
        var nodes = <div>No</div>,
            self = this;
        if (this.state.items.length == 0) {
            nodes = <div>No items currently in cart!</div>
        } else {
            nodes = this.state.items.map(function (item) {
                return (
                    <ShoppingCartRow key={item.product.id} product={item.product} count={item.count} url={self.props.url}></ShoppingCartRow>
                    );
            });
        }

        return(<div className="shopping-cart-list">
            <div className="header"><span>Your Cart:</span><a className="close">X</a></div>
        {nodes}
            <div className="footer"><a className="btn primary empty" onClick={this.clearCart}>Clear All</a></div>
        </div>);
    }

});


React.render(<ProductList url="/product/" />, document.getElementById('productList'));
React.render(<ShoppingCartCounter url="/cart/count" />, document.getElementById('counter'));
React.render(<ShoppingCartList url="/cart" />, document.getElementById('cartList'));