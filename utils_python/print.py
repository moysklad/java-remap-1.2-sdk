from datetime import datetime

def _print_t(msg: str) -> None:
    _t = datetime.now().strftime("%H:%M:%S")
    print(_t, ' | ', msg)
    return