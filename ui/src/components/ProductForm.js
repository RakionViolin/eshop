const ProductForm = ({product, handleChange, handleSubmit}) => {
    const inputs = Object.keys({product}.product).map((k) => {
        return(
            <div class="form-group row">
                    <label for={k} class="col-sm-2 col-form-label">{k.toUpperCase()}</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id={k} placeholder={'Product '+k} value={{product}.product[k]} onChange={handleChange}></input>
                    </div>
            </div>
        )            
    });
    return (
        <div class="card border-success" style={{width:'80%', margin:'0 auto'}}>
            <h3 class="card-title text-center text-success">Product</h3>
            <form onSubmit={handleSubmit}>    
                {inputs}
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>    
                
            </form>
        </div>
    );
}

export default ProductForm;
