const Select = ({desc, n, setQuantity}) => {
    let ops =[];
    for (let i=1; i<=n; i++)
        ops.push(<option value={i}>{i}</option>);
    return (
        <label>{desc}:&nbsp;
            <select name={desc} onChange={e => setQuantity(e.target.value)}>
                {ops}
            </select>
        </label>
    )

}

export default Select;