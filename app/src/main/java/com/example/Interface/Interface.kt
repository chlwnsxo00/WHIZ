package com.example.Interface

import com.example.obj.sites


interface ItemListener {
    fun onClicked(name : sites)
}

interface SiteClickListener{
    fun onSiteClicked(site : sites)
}