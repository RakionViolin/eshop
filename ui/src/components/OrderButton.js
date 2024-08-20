import {useState, useEffect} from 'react';

function OrderButton ({quantity, handleDelete, id, handleUpdate}){
  const [count, setCount] = useState(parseInt(quantity));
  
  useEffect(() => {
    handleUpdate(id,count);
      if(count === 0){
        handleDelete(id);
      }     

  }, [count])
 
  
    return(
      <div class="btn-group">
        <button type="button" class="btn btn-warning" onClick={()=>setCount(count+1)}>
          <i class="bi bi-plus"></i>
        </button>
        <button class="btn btn-success">{count}</button>
        <button type="button" class="btn btn-warning" onClick={()=>setCount(count-1)}>
          <i class="bi bi-dash"></i>
        </button>
      </div>
     
    );
  }

  export default OrderButton;